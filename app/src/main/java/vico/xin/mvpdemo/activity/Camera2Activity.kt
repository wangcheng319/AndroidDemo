package vico.xin.mvpdemo.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.ActivityCompat
import android.util.SparseIntArray
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import java.nio.ByteBuffer
import java.util.Arrays

import vico.xin.mvpdemo.R

/**
 * Created by wangc on 2017/10/30
 * E-MAIL:274281610@QQ.COM
 */

class Camera2Activity : Activity() {

    private var imageView: ImageView? = null
    private var button: Button? = null

    private var surfaceView: SurfaceView? = null
    private var surfaceHolder: SurfaceHolder? = null

    private var cameraManager: CameraManager? = null
    private var cameraDevice: CameraDevice? = null
    private var cameraCaptureSession: CameraCaptureSession? = null
    private var imageReader: ImageReader? = null
    private var mCameraID: Int = 0
    private val REQUEST_CAMERA_CODE = 101
    private var childHandler: Handler? = null
    private var mainHandler: Handler? = null


     var stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            //摄像头打开
            cameraDevice = camera
            //开始预览
            takePreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            //摄像头关闭
            if (null != cameraDevice) {
                cameraDevice!!.close()
            }
        }

        override fun onError(camera: CameraDevice, error: Int) {
            //打开摄像头错误
        }
    }


     var surfaceCallBack: SurfaceHolder.Callback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            initCamera()
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // 释放Camera资源
            if (null != cameraDevice) {
                cameraDevice!!.close()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera2)

        initView()
    }

    private fun initView() {
        surfaceView = findViewById(R.id.sf_view) as SurfaceView
        surfaceHolder = surfaceView!!.holder

        surfaceHolder!!.addCallback(surfaceCallBack)

        imageView = findViewById(R.id.iv) as ImageView
        button = findViewById(R.id.take) as Button

        button!!.setOnClickListener { takePicture() }

    }


    private fun initCamera() {
        val handlerThread = HandlerThread("Camera2")
        handlerThread.start()
        childHandler = Handler(handlerThread.looper)
        mainHandler = Handler(mainLooper)

        //获取摄像头管理
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        mCameraID = CameraCharacteristics.LENS_FACING_FRONT
        imageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1)
        imageReader!!.setOnImageAvailableListener({ reader ->
            //处理拍照后得到的图片
            val image = reader.acquireNextImage()
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)//由缓冲区存入字节数组
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            if (bitmap != null) {
                imageView?.setImageBitmap(bitmap)
            }

            image.close()
        }, mainHandler)


        //申请相机需要的权限
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                requestPermissions(arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CAMERA_CODE)
                //return;
            } else {
                //打开摄像头
                cameraManager!!.openCamera(mCameraID.toString() + "", stateCallback, mainHandler)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }


    /**
     * 开始拍照
     */
    private fun takePicture() {
        if (cameraDevice == null) return
        // 创建拍照需要的CaptureRequest.Builder
        val captureRequestBuilder: CaptureRequest.Builder
        try {
            captureRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(imageReader!!.surface)
            // 自动对焦
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
            // 自动曝光
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
            // 获取手机方向
            val rotation = windowManager.defaultDisplay.rotation
            // 根据设备方向计算设置照片的方向
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))
            //拍照
            val mCaptureRequest = captureRequestBuilder.build()
            cameraCaptureSession!!.capture(mCaptureRequest, null, childHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }


    /**
     * 开始预览
     */
    private fun takePreview() {
        try {
            val previewRequestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            previewRequestBuilder.addTarget(surfaceHolder!!.surface)

            cameraDevice!!.createCaptureSession(Arrays.asList(surfaceHolder!!.surface, imageReader!!.surface), object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    if (null == cameraDevice) {
                        return
                    }
                    // 当摄像头已经准备好时，开始显示预览
                    cameraCaptureSession = session
                    try {
                        // 自动对焦
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                        // 打开闪光灯
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                        // 显示预览
                        val previewRequest = previewRequestBuilder.build()
                        cameraCaptureSession!!.setRepeatingRequest(previewRequest, null, childHandler)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }

                }

                override fun onConfigureFailed(session: CameraCaptureSession) {
                    Toast.makeText(this@Camera2Activity, "配置失败", Toast.LENGTH_SHORT).show()
                }
            }, childHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                try {
                    cameraManager!!.openCamera(mCameraID.toString() + "", stateCallback, mainHandler)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }

            } else {
                // Permission Denied
            }
        }
    }

    companion object {

        private val ORIENTATIONS = SparseIntArray()

        ///为了使照片竖直显示
        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
    }
}

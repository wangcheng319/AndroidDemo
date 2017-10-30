package vico.xin.mvpdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

public class BitmapUtil3 {

	public static Bitmap decodeBitmapFromResponse(String path, int reqWidth) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 不读去图片到内存中,只解析图片的宽高
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		// 获取图片的宽度
		int width = options.outWidth;
		int height = options.outHeight;
		// 图片压缩的比率
		int inSmapleSize = 1;
		// 判断图片的宽高，如果宽度大于高度，则按宽度比率压缩，否则按高度比率压缩
		if (width > height) {
			if (width > reqWidth) {
				inSmapleSize = Math.round((float) width / (float) reqWidth);
			}
		} else {
			inSmapleSize = Math.round((float) height / (float) reqWidth);
		}

		// 读取图片本身
		options.inJustDecodeBounds = false;
		options.inSampleSize = inSmapleSize;
		return BitmapFactory.decodeFile(path, options);
	}

	public static void saveBitmapFile(Bitmap bitmap, String fileName) {
		File file = new File(fileName);// 将要保存图片的路径
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
			Log.e("===","bitmap保存成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap byteToBitmap(byte[] imgByte) {
		{
			InputStream input = null;
			Bitmap bitmap = null;
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
			input = new ByteArrayInputStream(imgByte) {
			};
			SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
					input, null, options));
			bitmap = (Bitmap) softRef.get();
			if (imgByte != null) {
				imgByte = null;
			}

			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

	}
}

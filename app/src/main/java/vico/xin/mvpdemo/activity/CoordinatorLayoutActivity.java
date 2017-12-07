package vico.xin.mvpdemo.activity;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

import vico.xin.mvpdemo.R;
import vico.xin.mvpdemo.dto.DaoSession;
import vico.xin.mvpdemo.video.InputSurface;
import vico.xin.mvpdemo.video.OutputSurface;
import vico.xin.mvpdemo.video.SampleInfo;
import vico.xin.mvpdemo.video.TrackInfo;


public class CoordinatorLayoutActivity extends AppCompatActivity {
    ImageView iv ;
    TrackInfo trackInfo;
    MediaMuxer     muxer;
    MediaFormat outputVideoMediaFormat;//输出文件格式
     MediaCodec encoder;
     MediaCodec decoder;
    MediaExtractor mediaExtractor;
    private boolean videoEncoderDone = false;
    private int outputVideoTrackIndex = -1;
    private boolean videoExtractorDone;
    private boolean videoDecoderDone;
    private HandlerThread decodeHandlerThread;
    private Queue<SampleInfo> pendingVideoEncoderOutputBufferInfos = new LinkedList<>();
    private boolean muxerStarted;
    InputSurface inputSurface;
    OutputSurface outputSurface;

    DaoSession daoSession;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        textView  = (TextView) findViewById(R.id.text);




    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.e("===","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("===","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("===","onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.e("===","onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.e("===","onDestroy");
    }
}

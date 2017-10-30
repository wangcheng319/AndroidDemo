package vico.xin.mvpdemo.video;

import android.media.MediaCodec;

/**
 * Created by wangc on 2017/10/20
 * E-MAIL:274281610@QQ.COM
 */

public class SampleInfo {
        public int index;
        public MediaCodec.BufferInfo bufferInfo;

        public SampleInfo(int index, MediaCodec.BufferInfo bufferInfo) {
            this.index = index;
            this.bufferInfo = bufferInfo;
        }
}

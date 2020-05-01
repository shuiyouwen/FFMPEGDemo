package com.example.ffmpegdemo.record;

import android.media.AudioFormat;

public class GlobalConfig {
    //采样率
    public static final int SAMPLE_RATE_INHZ = 44100;
    //声道
    public static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    //音频格式
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
}

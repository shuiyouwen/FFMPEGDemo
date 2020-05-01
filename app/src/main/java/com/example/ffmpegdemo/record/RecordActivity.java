package com.example.ffmpegdemo.record;

import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ffmpegdemo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.media.AudioTrack.MODE_STATIC;
import static com.example.ffmpegdemo.record.GlobalConfig.*;

public class RecordActivity extends AppCompatActivity {
    private boolean isRecording;
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    public void record(View view) {
        final int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize);
        audioRecord.startRecording();
        isRecording = true;

        final byte[] data = new byte[minBufferSize];
        final File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.pcm");
        Log.d("RecordActivity", "test.pcm 路径：" + file.getPath());
        if (!file.mkdirs()) {
            Log.e("RecordActivity", "文件夹创建失败");
        }
        if (file.exists()) {
            file.delete();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    while (isRecording) {
                        int read = audioRecord.read(data, 0, minBufferSize);
                        if (read != AudioRecord.ERROR_INVALID_OPERATION) {
                            fos.write(data);
                        }
                    }
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void stopRecord(View view) {
        isRecording = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    public void playPCMByStatic(View view) {
        AsyncTask<Void, Void, byte[]> asyncTask = new AsyncTask<Void, Void, byte[]>() {
            @Override
            protected byte[] doInBackground(Void... voids) {
                final File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.pcm");
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    for (int b; (b = fis.read()) != -1; ) {
                        bos.write(b);
                    }
                    return bos.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(byte[] bytes) {
                int minBufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT);
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize, MODE_STATIC);
                audioTrack.write(bytes, 0, bytes.length);
                audioTrack.play();
            }
        };
        asyncTask.execute();
    }

    public void stopPlay(View view) {
        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
        }
    }
}

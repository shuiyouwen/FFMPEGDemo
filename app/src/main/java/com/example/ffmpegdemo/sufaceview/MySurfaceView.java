package com.example.ffmpegdemo.sufaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ffmpegdemo.R;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback2, Runnable {


    private Paint paint;
    private Bitmap bitmap;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.cms_match_playing);
        if (bitmap == null) {
            Log.d("MySurfaceView", "bitmap创建失败");
        }
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        Canvas canvas = getHolder().lockCanvas();
        if (bitmap == null) {
            Log.d("MySurfaceView", "bitmap is null");
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
        getHolder().unlockCanvasAndPost(canvas);
    }
}

package com.yanzhikai.androidopengldemo.EGLFace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yanzhikai.androidopengldemo.R;

public class EGLFaceActivity extends AppCompatActivity {

    private SurfaceView svEglFace;
    private GLRenderThread mRenderThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eglface);
        mRenderThread = new GLRenderThread("face");
        mRenderThread.start();
        initView();
    }

    private void initView() {
        svEglFace = (SurfaceView) findViewById(R.id.sv_egl_face);
        svEglFace.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mRenderThread.render(holder.getSurface(),width,height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
}

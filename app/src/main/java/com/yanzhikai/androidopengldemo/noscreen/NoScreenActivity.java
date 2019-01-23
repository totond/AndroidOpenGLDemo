package com.yanzhikai.androidopengldemo.noscreen;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yanzhikai.androidopengldemo.BitmapUtil;
import com.yanzhikai.androidopengldemo.R;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class NoScreenActivity extends AppCompatActivity {
    private TestRenderer mTestRenderer;
    private ImageView ivShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_screen);
        initView();
    }

    private void initView() {
        ivShow = (ImageView) findViewById(R.id.iv_show);
        mTestRenderer = new TestRenderer();
        GLSurface glPbufferSurface = new GLSurface(512,512);
        mTestRenderer.addSurface(glPbufferSurface);
        mTestRenderer.startRender();
        mTestRenderer.requestRender();

        mTestRenderer.postRunnable(new Runnable() {
            @Override
            public void run() {
                IntBuffer ib = IntBuffer.allocate(512 * 512);
                GLES20.glReadPixels(0, 0, 512, 512, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);

                final Bitmap bitmap = BitmapUtil.frameToBitmap(512, 512, ib);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ivShow.setImageBitmap(bitmap);
                    }
                });
            }
        });

    }


}

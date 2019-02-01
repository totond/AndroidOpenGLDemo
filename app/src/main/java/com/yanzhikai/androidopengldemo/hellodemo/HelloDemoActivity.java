package com.yanzhikai.androidopengldemo.hellodemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yanzhikai.androidopengldemo.R;

public class HelloDemoActivity extends AppCompatActivity {

    private GLSurfaceView glsvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_demo);
        initView();
    }

    private void initView() {
        glsvHello = (GLSurfaceView) findViewById(R.id.glsv_hello);
        glsvHello.setEGLContextClientVersion(2);
        glsvHello.setRenderer(new HelloRender2(this));
        glsvHello.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        
    }
}

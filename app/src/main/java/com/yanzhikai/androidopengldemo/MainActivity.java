package com.yanzhikai.androidopengldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yanzhikai.androidopengldemo.EGLFace.EGLFaceActivity;
import com.yanzhikai.androidopengldemo.hellodemo.HelloDemoActivity;
import com.yanzhikai.androidopengldemo.noscreen.NoScreenActivity;
import com.yanzhikai.androidopengldemo.video.VideoHandleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnHelloDemo;
    private Button btnEglFaceDemo;
    private Button btnNoScreenDemo;
    private Button btnVideoHandleDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnHelloDemo = (Button) findViewById(R.id.btn_hello_demo);
        btnEglFaceDemo = (Button) findViewById(R.id.btn_egl_face_demo);

        btnHelloDemo.setOnClickListener(this);
        btnEglFaceDemo.setOnClickListener(this);
        btnNoScreenDemo = (Button) findViewById(R.id.btn_no_screen_demo);
        btnNoScreenDemo.setOnClickListener(this);
        btnVideoHandleDemo = (Button) findViewById(R.id.btn_video_handle_demo);
        btnVideoHandleDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hello_demo:
                ActivityJumpUtil.startActivity(this, HelloDemoActivity.class);
                break;
            case R.id.btn_egl_face_demo:
                ActivityJumpUtil.startActivity(this, EGLFaceActivity.class);
                break;
            case R.id.btn_no_screen_demo:
                ActivityJumpUtil.startActivity(this, NoScreenActivity.class);
                break;
            case R.id.btn_video_handle_demo:
                ActivityJumpUtil.startActivity(this, VideoHandleActivity.class);
                break;
        }
    }
}

package com.yanzhikai.androidopengldemo.hellodemo;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.yanzhikai.androidopengldemo.MatrixHelper;
import com.yanzhikai.androidopengldemo.R;
import com.yanzhikai.androidopengldemo.TextureUtil;
import com.yanzhikai.androidopengldemo.hellodemo.data.Mallet;
import com.yanzhikai.androidopengldemo.hellodemo.data.Puck;
import com.yanzhikai.androidopengldemo.hellodemo.data.Table;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HelloRender2 implements GLSurfaceView.Renderer{
    private Table table;
    private Mallet mallet;
    private Puck puck;
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;


    private int textureId;
    private Context context;

    private final float[] viewMatrix = new float[16];
    private float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] uMatrix = new float[16];

    private final float[] viewProjectionMatrix = new float[16];

    public HelloRender2(Context context){
        this.context = context;


        Matrix.setIdentityM(modelMatrix,0);
        Matrix.setIdentityM(viewMatrix,0);


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);


        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);

        textureId = TextureUtil.loadTexture(context, R.mipmap.air_hockey_surface);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float)width/(float)height, 1f, 100f);
        Matrix.setLookAtM(viewMatrix, 0,
                0f, 1.2f, 2.2f,
                0f, 0f, 0f,
                0f, 1f, 0f);

        Matrix.multiplyMM(viewProjectionMatrix,0,  projectionMatrix,0, viewMatrix,0);

        Matrix.rotateM(table.modelMatrix,0, -90f, 1f,0f,0f);
        Matrix.translateM(mallet.modelMatrix,0, 0f, mallet.height, 0.5f);
        Matrix.translateM(puck.modelMatrix,0, 0f, puck.height, 0f );
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(uMatrix, 0, viewProjectionMatrix, 0, table.modelMatrix, 0);
        textureShaderProgram.userProgram();
        textureShaderProgram.setUniforms(uMatrix, textureId);
        table.bindData(textureShaderProgram);
        table.draw();

        Matrix.multiplyMM(uMatrix, 0, viewProjectionMatrix, 0, mallet.modelMatrix, 0);
        colorShaderProgram.userProgram();
        colorShaderProgram.setUniforms(uMatrix, 1f, 0f, 0f);
        mallet.bindData(colorShaderProgram);
        mallet.draw();

        Matrix.multiplyMM(uMatrix, 0, viewProjectionMatrix, 0, puck.modelMatrix, 0);
        colorShaderProgram.userProgram();
        colorShaderProgram.setUniforms(uMatrix, 0f, 1f, 0f);
        puck.bindData(colorShaderProgram);
        puck.draw();
    }
}

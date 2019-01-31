package com.yanzhikai.androidopengldemo.hellodemo;

import android.content.Context;
import android.opengl.GLES20;

import com.yanzhikai.androidopengldemo.GLUtil;

public class ShaderProgram {
    protected final int programId;

    public ShaderProgram(String vertexShaderResourceStr,
                         String fragmentShaderResourceStr){
        programId = GLUtil.linkProgram(
                vertexShaderResourceStr,
                fragmentShaderResourceStr);
    }

    public ShaderProgram(Context context, int vertexShaderResourceId,
                         int fragmentShaderResourceId){
        programId = GLUtil.linkProgram(
                GLUtil.readTextFileFromResource(context,vertexShaderResourceId),
                GLUtil.readTextFileFromResource(context,fragmentShaderResourceId));
    }

    public void userProgram() {
        GLES20.glUseProgram(programId);
    }
    public int getShaderProgramId() {
        return programId;
    }

    public void deleteProgram() {
        GLES20.glDeleteProgram(programId);
    }
}

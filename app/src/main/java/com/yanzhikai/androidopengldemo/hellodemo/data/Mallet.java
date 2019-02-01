package com.yanzhikai.androidopengldemo.hellodemo.data;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yanzhikai.androidopengldemo.Constants;
import com.yanzhikai.androidopengldemo.hellodemo.ColorShaderProgram;

import java.util.List;


public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    private final VertexArray vertexArray;
    private List<ObjectBuilder.DrawCommand> drawList;


    public float[] modelMatrix = new float[16];

    public final float raduis;
    public final float height;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        ObjectBuilder.GeneratedData mallet = ObjectBuilder.createMallet(
                new Geometry.Point(0f, 0f, 0f),
                radius, height, numPointsAroundMallet);
        this.raduis = radius;
        this.height = height;

        vertexArray = new VertexArray(mallet.vertexData);
        drawList = mallet.drawCommandlist;

        Matrix.setIdentityM(modelMatrix,0);
    }

    public void bindData(ColorShaderProgram shaderProgram) {
        vertexArray.setVertexAttributePointer(
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT, 0, 0
        );
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand command : drawList) {
            command.draw();
        }
    }
}

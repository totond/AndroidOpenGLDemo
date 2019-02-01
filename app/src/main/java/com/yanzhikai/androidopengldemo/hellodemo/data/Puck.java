package com.yanzhikai.androidopengldemo.hellodemo.data;

import android.opengl.Matrix;

import com.yanzhikai.androidopengldemo.hellodemo.ColorShaderProgram;

import java.util.List;

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public float[] modelMatrix = new float[16];

    public Puck(float radius, float height, int numPointsAroundPuck) {
        ObjectBuilder.GeneratedData puck = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height),
                numPointsAroundPuck
        );

        vertexArray = new VertexArray(puck.vertexData);
        drawList = puck.drawCommandlist;

        Matrix.setIdentityM(modelMatrix,0);

        this.radius = radius;
        this.height = height;
    }

    public void bindData(ColorShaderProgram shaderProgram) {
        vertexArray.setVertexAttributePointer(
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                0, 0
        );
    }

    public void draw() {
        for(ObjectBuilder.DrawCommand command : drawList) {
            command.draw();
        }
    }

}

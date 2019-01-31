package com.yanzhikai.androidopengldemo.hellodemo;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.yanzhikai.androidopengldemo.GLUtil;
import com.yanzhikai.androidopengldemo.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.yanzhikai.androidopengldemo.GLUtil.linkProgram;
import static com.yanzhikai.androidopengldemo.GLUtil.fragmentShader;
import static com.yanzhikai.androidopengldemo.GLUtil.verticesShader;

public class HelloRender implements GLSurfaceView.Renderer {

    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;


    private int program;
    private int aPositionLocation;

//    private int uColor;
    private String vertexShaderSource, fragmentShaderSource;
    private static final String A_COLOR = "a_Color";
    private int aColorLocation;

    private static final String U_MATRIX = "u_Matrix";
    private int uMatrixLocation;
    private float[] projectionMatrix = new float[16];


    public HelloRender(Context context) {
        vertexShaderSource = GLUtil.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        fragmentShaderSource = GLUtil.readTextFileFromResource(context, R.raw.simple_fragment_shader);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {


        // 使用某套shader程序
        program = linkProgram(vertexShaderSource, fragmentShaderSource);
        GLES20.glUseProgram(program);
        // 获取着色器中的属性引用id(传入的字符串就是我们着色器脚本中的属性名)
        aPositionLocation = GLES20.glGetAttribLocation(program, "a_Position");
//        uColor = GLES20.glGetUniformLocation(program, "u_Color");
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        gl.glClearColor(0, 0, 0, 0);

        // 获取图形的顶点坐标
        FloatBuffer vertexData = getVertices();
        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        vertexData.position(2);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT,
                GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置绘图的窗口(可以理解成在画布上划出一块区域来画图)
        GLES20.glViewport(0, 0, width, height);

        final float aspectRatio = width > height ?
                (float)width / (float)height   :
                (float)height / (float)width ;
        if(width > height){
            Matrix.orthoM(projectionMatrix,0, -aspectRatio, aspectRatio,   -1f,1f,    -1f,1f);
        } else {
            Matrix.orthoM(projectionMatrix,0, -1f,1f,    -aspectRatio, aspectRatio,   -1f,1f);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(uMatrixLocation,1, false,  projectionMatrix,0);

        // 设置属性uColor(颜色 索引,R,G,B,A)
//        GLES20.glUniform4f(aColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
//        GLES20.glUniform4f(aColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
//        GLES20.glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
//        GLES20.glUniform4f(aColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);

    }


    /**
     * 获取图形的顶点
     * 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
     * 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
     *
     * @return 顶点Buffer
     */
    private FloatBuffer getVertices() {
        float vertices[] = {

                //  X, Y,        R, G, B
                // 三角扇形
                0, 0,           1f, 1f, 1f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                // 中间的分界线
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,
                // 两个木槌的质点位置
                0f, -0.4f, 0f, 0f, 1f,
                0f, 0.4f, 1f, 0f, 0f,

        };

       /* ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices)
                .position(0);可以取代下面代码*/

        // 创建顶点坐标数据缓冲
        // vertices.length*4是因为一个float占四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());             //设置字节顺序
        FloatBuffer vertexBuf = vbb.asFloatBuffer();    //转换为Float型缓冲
        vertexBuf.put(vertices);                        //向缓冲区中放入顶点坐标数据
        vertexBuf.position(0);                          //设置缓冲区起始位置

        return vertexBuf;
    }

}

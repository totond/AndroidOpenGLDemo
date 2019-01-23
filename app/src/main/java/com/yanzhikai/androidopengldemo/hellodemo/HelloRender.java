package com.yanzhikai.androidopengldemo.hellodemo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.yanzhikai.androidopengldemo.GLUtil.linkProgram;
import static com.yanzhikai.androidopengldemo.GLUtil.fragmentShader;
import static com.yanzhikai.androidopengldemo.GLUtil.verticesShader;

public class HelloRender implements GLSurfaceView.Renderer {

    private int program;
    private int vPosition;
    private int uColor;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 使用某套shader程序
        program = linkProgram(verticesShader, fragmentShader);
        GLES20.glUseProgram(program);
        // 获取着色器中的属性引用id(传入的字符串就是我们着色器脚本中的属性名)
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        uColor = GLES20.glGetUniformLocation(program, "uColor");

        gl.glClearColor(0,0,0,0);

        // 获取图形的顶点坐标
        FloatBuffer vertices = getVertices();
        vertices.position(0);
        // 为画笔指定顶点位置数据(vPosition)
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertices);
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(vPosition);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置绘图的窗口(可以理解成在画布上划出一块区域来画图)
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清屏
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        // 设置属性uColor(颜色 索引,R,G,B,A)
        GLES20.glUniform4f(uColor, 0.0f, 1.0f, 0.0f, 1.0f);
        // 绘制，参数：画三角形、从顶点数组的开头处开始读顶点、读入3个顶点
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);

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
                0.0f,   0.5f,
                -0.5f, -0.5f,
                0.5f,  -0.5f,
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

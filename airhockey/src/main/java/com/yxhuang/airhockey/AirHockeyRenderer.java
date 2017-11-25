package com.yxhuang.airhockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.yxhuang.airhockey.util.LoggerConfig;
import com.yxhuang.airhockey.util.ShaderHelper;
import com.yxhuang.airhockey.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yxhuang
 * Date: 2017/11/18
 * Description:
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private Context mContext;

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer mVertexData;

    private int mProgram;

    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;


    public AirHockeyRenderer(Context context){
        mContext = context;

        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f,
        };

        float[] tableVerticesWithTriangles = {
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                // Line 1
                -0.5f, 0f,
                0.5f, 0f,

                // Mallets
                0f, -0.25f,
                0f, 0.25f,
        };

        mVertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexData.put(tableVerticesWithTriangles);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置清屏颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // 1. 获取 sourceCode.
        String vertexShaderSource = TextResourceReader.readTextFilerFromResource(mContext,
                "airhockey/simple_vertex_shader.glsl");
        String fragmentShaderSource = TextResourceReader.readTextFilerFromResource(mContext,
                "airhockey/simple_fragment_shader.glsl");
        // 2. 编译 ResourceCode
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        // 3. 连接 vertexShader 和 fragmentShader，生成 Program
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON){
            ShaderHelper.validateProgram(mProgram);
        }
        // 4. 使用 Program
        GLES20.glUseProgram(mProgram);

        // 获取位置
        uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        // 关联属性与定点数据的数组
        mVertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
                                    false, 0, mVertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // 绘制桌子
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // 绘制分割线
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        // 绘制两个木槌
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);


    }
}

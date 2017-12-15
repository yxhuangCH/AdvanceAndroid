package com.yxhuang.airhockey.objects;

import android.opengl.GLES20;

import com.yxhuang.airhockey.Constants;
import com.yxhuang.airhockey.data.VertexArray;
import com.yxhuang.airhockey.programs.TextureShaderProgram;

/**
 * Created by yxhuang
 * Date: 2017/12/12
 * Description: 桌子
 */

public class Table {

    // 分量计数
    private static final int POSITION_COMPONENT_COUNT = 2;
    // 纹理坐标
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    // 跨距
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;


    // 定点数据
    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T

            // Triangle Fan
            0f,       0f,    0.5f,  0.5f,
            -0.5f, -0.8f,    0f,    0.9f,
            0.5f,  -0.8f,    1f,    0.9f,
            0.5f,  0.8f,     1f,    0.1f,
            -0.5f, 0.8f,     0f,    0.1f,
            -0.5f, -0.8f,    0f,    0.9f
    };

    private final VertexArray mVertexArray;

    public Table(){
        mVertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureShaderProgram){
        mVertexArray.setVertexAttribPointer(
                0,
                textureShaderProgram.getaPositionLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        mVertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureShaderProgram.getaTextureCoordintesLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}

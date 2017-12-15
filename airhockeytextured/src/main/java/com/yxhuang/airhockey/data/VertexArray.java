package com.yxhuang.airhockey.data;

import android.opengl.GLES20;

import com.yxhuang.airhockey.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by yxhuang
 * Date: 2017/12/12
 * Description:
 */

public class VertexArray {

    private final FloatBuffer mFloatBuffer;

    public VertexArray(float[] vertexData){
        mFloatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride){
        mFloatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT,
                false, stride, mFloatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        mFloatBuffer.position(0);
    }

}

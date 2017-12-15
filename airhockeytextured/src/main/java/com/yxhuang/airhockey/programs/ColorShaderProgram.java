package com.yxhuang.airhockey.programs;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by yxhuang
 * Date: 2017/12/14
 * Description:
 */

public class ColorShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int uMatrixLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;


    public ColorShaderProgram(Context context) {
        super(context,
                "airhockey/simple_vertex_shader.glsl",
                "airhockey/simple_fragment_shader.glsl");
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITON);
        aColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR);
    }

    public void setUniforms(float[] matrix){
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }

    public int getaColorLocation() {
        return aColorLocation;
    }
}

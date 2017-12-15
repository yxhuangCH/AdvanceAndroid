package com.yxhuang.airhockey.programs;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by yxhuang
 * Date: 2017/12/12
 * Description:
 */

public class TextureShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordintesLocation;

    public TextureShaderProgram(Context context) {
        super(context, "airhockey/texture_vertex_shader.glsl", "airhockey/texture_fragment_shader.glsl");

        // Retrieve uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(mProgram, U_TEXTURE_UNIT);

        // Retrieve attribute locations for the shader program.
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITON);
        aTextureCoordintesLocation = GLES20.glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId){
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        // Set the active texture unit to texture unit 0.
        // 把活动的纹理单元设置为纹理单元 0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        // Tell the texture uniform sampler to use this texture int the shader by telling
        // it to read from texture unit 0.
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getaPositionLocation() {
        return aPositionLocation;
    }

    public int getaTextureCoordintesLocation() {
        return aTextureCoordintesLocation;
    }
}

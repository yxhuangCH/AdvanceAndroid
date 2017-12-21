package com.yxhuang.airhockey.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.yxhuang.airhockey.util.ShaderHelper;
import com.yxhuang.airhockey.util.TextResourceReader;

/**
 * Created by yxhuang
 * Date: 2017/12/12
 * Description:
 */

public class ShaderProgram {

    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String A_POSITON = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected static final String U_COLOR = "u_Color";

    // Shader program
    protected final int mProgram;

    protected ShaderProgram(Context context, String vertexShaderResourcePath, String fragmentShaderResourcePath){
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextFilerFromResource(context, vertexShaderResourcePath),
                    TextResourceReader.readTextFilerFromResource(context, fragmentShaderResourcePath));
    }

    public void useProgram(){
        // Set the current OpenGl shader program to this program.
        GLES20.glUseProgram(mProgram);
    }
}

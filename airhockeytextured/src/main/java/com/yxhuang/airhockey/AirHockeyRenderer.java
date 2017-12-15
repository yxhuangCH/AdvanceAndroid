package com.yxhuang.airhockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.yxhuang.airhockey.objects.Mallet;
import com.yxhuang.airhockey.objects.Table;
import com.yxhuang.airhockey.programs.ColorShaderProgram;
import com.yxhuang.airhockey.programs.TextureShaderProgram;
import com.yxhuang.airhockey.util.MatrixHelper;
import com.yxhuang.airhockey.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yxhuang
 * Date: 2017/11/18
 * Description:
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private Context mContext;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table mTable;
    private Mallet mMallet;

    private TextureShaderProgram mTextureShaderProgram;
    private ColorShaderProgram mColorShaderProgram;

    private int mTexture;




    public AirHockeyRenderer(Context context){
        mContext = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置清屏颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mTable = new Table();
        mMallet = new Mallet();

        mTextureShaderProgram = new TextureShaderProgram(mContext);
        mColorShaderProgram = new ColorShaderProgram(mContext);

        mTexture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

//        final float aspestRatio = width > height ?
//                (float)width / (float)height : (float)height / (float)width;
//        if (width > height){
//            // 横屏
//            Matrix.orthoM(projectionMatrix, 0, -aspestRatio, aspestRatio,
//                    -1f, 1f, -1f, 1f);
//        } else { // 竖屏
//            Matrix.orthoM(projectionMatrix, 0, -1f, 1f,
//                    -aspestRatio, aspestRatio, -1f, 1f);
//        }
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);

        /*
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -2f);
        */

        Matrix.setIdentityM(modelMatrix, 0);

        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // 绘制桌子
        mTextureShaderProgram.useProgram();;
        mTextureShaderProgram.setUniforms(projectionMatrix, mTexture);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();

        // 绘制两个木槌
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(projectionMatrix);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();

    }
}

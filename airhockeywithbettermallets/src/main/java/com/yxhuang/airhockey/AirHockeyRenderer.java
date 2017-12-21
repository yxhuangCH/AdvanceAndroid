package com.yxhuang.airhockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.yxhuang.airhockey.objects.Mallet;
import com.yxhuang.airhockey.objects.Puck;
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

    private final float[] mViewMatrix = new float[16];
    private final float[] mViewProjectMatrix = new float[16];
    private final float[] mModelViewProjectMatrix = new float[16];

    private Puck mPuck;

    public AirHockeyRenderer(Context context){
        mContext = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置清屏颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mTable = new Table();
        mMallet = new Mallet(0.08f, 0.15f, 32);
        mPuck = new Puck(0.6f, 0.02f, 32);

        mTextureShaderProgram = new TextureShaderProgram(mContext);
        mColorShaderProgram = new ColorShaderProgram(mContext);

        mTexture = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);

//        /*
//        setIdentityM(modelMatrix, 0);
//        translateM(modelMatrix, 0, 0f, 0f, -2f);
//        */
//
//        Matrix.setIdentityM(modelMatrix, 0);
//
//        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
//        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
//
//        final float[] temp = new float[16];
//        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
//        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 1.2f, 2.2f,
                0f, 0f, 0f,
                0f, 1f, 0f);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.multiplyMM(mViewProjectMatrix, 0, projectionMatrix, 0, mViewMatrix, 0);

        // 绘制桌子
        positionTableInScene();
        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(mModelViewProjectMatrix, mTexture);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();

        // 绘制两个木槌
        positionObjectInScene(0f, mMallet.mHeight / 2f, -0.4f);
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(mModelViewProjectMatrix, 1f, 0f, 0f);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();

        positionObjectInScene(0f, mMallet.mHeight / 2f, 0.4f);
        mColorShaderProgram.setUniforms(mModelViewProjectMatrix, 0f, 0f, 1f);
        mMallet.draw();

        positionObjectInScene(0f, mPuck.mHeight / 2f, 0f);
        mColorShaderProgram.setUniforms(mModelViewProjectMatrix, 0.8f, 0.8f, 1f);
        mPuck.bindData(mColorShaderProgram);
        mPuck.draw();

    }

    private void positionTableInScene(){
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(mModelViewProjectMatrix, 0, mViewProjectMatrix,
                0, modelMatrix, 0);

    }

    private void positionObjectInScene(float x, float y, float z){
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(mModelViewProjectMatrix, 0, mViewProjectMatrix,
                0, modelMatrix, 0);

    }
}

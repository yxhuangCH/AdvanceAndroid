package com.yxhuang.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by yxhuang
 * Date: 2017/11/18
 * Description:
 */

public class AirHockeyGLSurfaceView extends GLSurfaceView {

    private Renderer mRenderer;

    public AirHockeyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        mRenderer = new AirHockeyRenderer(context);
        setRenderer(mRenderer);
    }
}

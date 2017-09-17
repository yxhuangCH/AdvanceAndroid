package com.example.yxhuang.viewdemo;

import android.animation.TypeEvaluator;
import android.graphics.Color;

/**
 * Created by yxhuang
 * Date: 2017/9/17
 * Description: 自定义 HSV Evaluator
 */

public class HsvEvaluator implements TypeEvaluator<Integer> {
    float[] mStartHsv = new float[3];
    float[] mEndHsv = new float[3];
    float[] mOutHsv = new float[3];

    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        // 把 ARGB 转成 HSV
        Color.colorToHSV(startValue, mStartHsv);
        Color.colorToHSV(endValue, mEndHsv);

        // 计算当前动画完成度（fraction） 所对应的颜色值
        if (mEndHsv[0] - mStartHsv[0] > 180){
            mEndHsv[0] -= 360;
        } else if (mEndHsv[0] - mStartHsv[0] < -180){
            mEndHsv[0] +=360;
        }
        mOutHsv[0] = mStartHsv[0] + (mEndHsv[0] - mStartHsv[0]) * fraction;
        if (mOutHsv[0] > 360){
            mOutHsv[0] -= 360;
        } else if (mOutHsv[0] < 0){
            mOutHsv[0] +=360;
        }

        mOutHsv[1] = mStartHsv[1] + (mEndHsv[1] - mStartHsv[1]) * fraction;
        mOutHsv[2] = mStartHsv[2] + (mEndHsv[2] - mStartHsv[2]) * fraction;
        // 计算当前动画完成度（fraction） 所对应的透明度
        int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

        // 把 HSV 转回 ARGB, 并返回
        return Color.HSVToColor(alpha, mOutHsv);
    }
}

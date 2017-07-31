package com.example.yxhuang.viewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yxhuang on 2017/7/20.
 */

public class CustomView extends View {

    private Paint mPaint = new Paint();

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        drawCircle(canvas);
//        drawPoint(canvas);
//        drawPointMany(canvas);
//        drawRoundRect(canvas);
//        drawArc(canvas);
//    customDraw(canvas);
//        pathDrawLine(canvas);
//        pathCloseDraw(canvas);

//        customPaint(canvas);

        customDrawText(canvas);


    }

    // 绘制圆
    private void drawCircle(Canvas canvas){
        canvas.drawColor(Color.GRAY);   // 绘制背景颜色

        mPaint.setColor(Color.RED); // 画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);    // 设置绘制模式，这里是设置镂空
        mPaint.setStrokeWidth(10);      // 设置线宽
        mPaint.setAntiAlias(true);      // 是否开抗锯齿
        canvas.drawCircle(300, 300, 200, mPaint);   // 绘制圆
    }

    // 绘制圆点
    private void drawPoint(Canvas canvas){
        mPaint.setStrokeWidth(30);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);   // 绘制圆形
        mPaint.setStrokeCap(Paint.Cap.SQUARE);  // 绘制方形
        canvas.drawPoint(300, 300, mPaint);

    }

    // 批量画圆点
    private void drawPointMany(Canvas canvas){
        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        // 绘制四个点 (50, 50) (50, 100) (100, 50) (100, 100)
        mPaint.setStrokeWidth(30);
        canvas.drawPoints(points, 2, 8, mPaint);
    }

    // 圆角矩形
    private void drawRoundRect(Canvas canvas){
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        // api 21
//        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, mPaint);
        // 低版本
        RectF rect = new RectF();
        rect.left = 50;
        rect.top = 50;
        rect.right = 450;
        rect.bottom = 250;
        canvas.drawRoundRect(rect, 20, 20, mPaint);
    }

    // 绘制弧形或扇形
    private void drawArc(Canvas canvas){
        mPaint.setStyle(Paint.Style.FILL);
        // 绘制扇形
        canvas.drawArc(200, 100, 800, 500, -110, 100, true, mPaint);
        // 绘制弧形
        canvas.drawArc(200, 100, 800, 500, 20, 140, false, mPaint);
        // 画线模式
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(200, 100, 800, 500, 180, 60, false, mPaint);
    }

    // 自定义画
    private void customDraw(Canvas canvas){
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();

        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);

        canvas.drawPath(path, mPaint);
    }

    private void pathDrawLine(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
        path.lineTo(100, 100);
        // 由当前位置 (100, 100) 向右前方 100 像素画一条直线
        path.rLineTo(100, 0);

        canvas.drawPath(path, mPaint);
    }

    private void pathCloseDraw(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(200, 100);
        path.lineTo(150, 150);

        //使用 Close() 封闭子图形
        path.close();

        canvas.drawPath(path, mPaint);
    }

    // HenCoder Android 开发进阶: 自定义 View 1-2 Paint 详解
    private void customPaint(Canvas canvas){

//        paintShader(canvas);
        paintBitmapShader(canvas);

    }

    // 设置 shader
    private void paintShader(Canvas canvas){
//        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
//                                            Color.BLUE, Shader.TileMode.CLAMP);
//        Shader shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"), Color.BLUE, Shader.TileMode.CLAMP);

        Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"), Color.BLUE);

        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(300, 300, 300, mPaint);

    }

    private void paintBitmapShader(Canvas canvas){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        canvas.drawCircle(300, 300, 100, mPaint);

    }


    private void customDrawText(Canvas canvas){
//        drawText(canvas);
//        drawTextOnPath(canvas);
//        staticLayout(canvas);
//        setFakeBoldText(canvas);
//        setStrikeThruText(canvas);
//        setUnderlineText(canvas);
        setTextSkewX(canvas);

    }

    private void drawText(Canvas canvas){
        String text = "yxhuang";

        mPaint.setStrokeWidth(20);
        mPaint.setTextSize(100f);

        canvas.drawText(text, 200, 200, mPaint);
    }

    // 沿着一条 path 绘制文字
    private void drawTextOnPath(Canvas canvas) {
        String text = "yxhuang";

        mPaint.setStrokeWidth(20);
        mPaint.setTextSize(100f);

        Path path = new Path();
        path.addArc(200, 200, 400, 400, -225, 225);
//        path.arcTo(400, 200, 600, 400, -180, 225, false);
//        path.lineTo(400, 542);

        canvas.drawPath(path, mPaint);
        canvas.drawTextOnPath(text, path, 0, 0, mPaint);

    }

    private void staticLayout(Canvas canvas){
        String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

        TextPaint paint = new TextPaint();
        paint.setTextSize(100f);

        StaticLayout staticLayout1 = new StaticLayout(text1, paint, 600, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout1.draw(canvas);
    }

    // 是否使用伪粗体
    private void setFakeBoldText(Canvas canvas){
        TextPaint paint = new TextPaint();
        paint.setTextSize(100f);

        paint.setFakeBoldText(false);
        canvas.drawText("yxhuang", 100, 150, paint);

        paint.setFakeBoldText(true);
        canvas.drawText("yxhuang", 100, 250, paint);
    }

    // 是否加删除线
    private void setStrikeThruText(Canvas canvas){
        TextPaint paint = getTextPaint();
        paint.setStrikeThruText(true);
        canvas.drawText("yxhuang", 100, 250, paint);
    }

    // 是否加下划线
    private void setUnderlineText(Canvas canvas){
        TextPaint paint = getTextPaint();
        paint.setUnderlineText(true);
        canvas.drawText("yxhuang", 100, 250, paint);
    }

    // 设置文字倾斜度
    private void setTextSkewX(Canvas canvas){
        TextPaint paint = getTextPaint();
        paint.setTextSkewX(-0.5f);  // 负数向右倾斜，正数向左倾斜
        canvas.drawText("yxhuang", 100, 250, paint);

    }
    private TextPaint getTextPaint(){
        TextPaint paint = new TextPaint();
        paint.setTextSize(100f);
        return paint;
    }















































}

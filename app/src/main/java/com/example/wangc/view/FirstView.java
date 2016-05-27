package com.example.wangc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Wangc on 2016/5/17
 * E-MAIL:274281610@QQ.COM
 */
public class FirstView extends View {
    private Paint mPaint;
    private Canvas canvas;


    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstView(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);



        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.FILL, 5);
        Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect1 = new Rect(getWidth()/2,getHeight()/2,getWidth()/2+200,getHeight()/2+100);
        canvas.drawRect(rect1, paint_red); //画出原轮廓

        canvas.rotate(30);//顺时针旋转画布
        canvas.drawRect(rect1, paint_green);//画出旋转后的矩形
        //画虚线
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.DKGRAY);
//        Path path = new Path();
//        path.moveTo(0, 10);
//        path.lineTo(480, 10);
//        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
//        mPaint.setPathEffect(effects);
//        canvas.drawPath(path, mPaint);


    }

    private Paint generatePaint(int color,Paint.Style style,int width)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}

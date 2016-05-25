package com.example.wangc.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
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
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        canvas.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        canvas.drawCircle(0,0,50,mPaint);

        mPaint.setColor(Color.MAGENTA);
        Rect rect = new Rect(0,0,400,200);
        canvas.drawRect(rect ,mPaint);


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

package vico.xin.mvpdemo.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangc on 2017/11/8
 * E-MAIL:274281610@QQ.COM
 */

public class SunView extends View {

    private Paint paint;
    private int raidus = 50;
    ValueAnimator valueAnimator1;

    public void move(){
        final ValueAnimator valueAnimator = new ValueAnimator().ofFloat(1,2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float a = (float) animation.getAnimatedValue();
                Log.e("===",a+"");
                raidus = (int) (raidus+a);

                if (a > 1.9){
                    valueAnimator1.start();
                    raidus = 50;
                }

                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(100);
        valueAnimator.start();



        valueAnimator1 = new ValueAnimator().ofFloat(2,1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float a = (float) animation.getAnimatedValue();
                Log.e("===",a+"");
                raidus = (int) (raidus-a);

                if (a < 1.1){
                    valueAnimator.start();
                    raidus = 50;
                }

                invalidate();
            }
        });
        valueAnimator1.setDuration(1000);
        valueAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator1.setRepeatCount(100);

    }

    public SunView(Context context) {
        super(context);
        init();
    }


    public SunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(100,100,raidus,paint);
    }
}

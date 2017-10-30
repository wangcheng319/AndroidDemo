package vico.xin.mvpdemo.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import vico.xin.mvpdemo.R;

/**
 * Created by wangc on 2017/9/25
 * E-MAIL:274281610@QQ.COM
 */

public class TitleBehvior extends CoordinatorLayout.Behavior<Toolbar> {
    private static final String TAG = "TitleBehvior";
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;

    public TitleBehvior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar toolbar, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        startOffset = 0;
        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.header_height) - toolbar.getHeight();
        offset += dyConsumed;
        if (offset <= startOffset) {  //alpha为0
            toolbar.getBackground().setAlpha(0);
        } else if (offset > startOffset && offset < endOffset) { //alpha为0到255
            float precent = (float) (offset - startOffset) / endOffset;
            int alpha = Math.round(precent * 255);
            toolbar.getBackground().setAlpha(alpha);
        } else if (offset >= endOffset) {  //alpha为255
            toolbar.getBackground().setAlpha(255);
        }
    }
}

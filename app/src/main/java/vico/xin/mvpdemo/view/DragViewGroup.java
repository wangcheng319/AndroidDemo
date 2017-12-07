package vico.xin.mvpdemo.view;

import android.content.Context;
import android.support.v4.app.JobIntentService;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Created by wangc on 2017/11/29
 * E-MAIL:274281610@QQ.COM
 */

public class DragViewGroup extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private int mFirstLeft,mFirstTop;

    public DragViewGroup(Context context) {
        this(context,null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (child instanceof ImageView){
                    return false;
                }
                return true;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                //记录下初始位置
                mFirstLeft = capturedChild.getLeft();
                mFirstTop = capturedChild.getTop();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //left代表横向移动的边界值
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //view释放的时候回到初始位置
                mDragHelper.settleCapturedViewAt(mFirstLeft,mFirstTop);
                invalidate();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                //需要自己捕获需要边界处理的view
                mDragHelper.captureChildView(getChildAt(1),pointerId);
            }

        });

        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

}

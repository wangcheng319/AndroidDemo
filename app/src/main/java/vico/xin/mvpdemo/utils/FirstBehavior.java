package vico.xin.mvpdemo.utils;

import android.graphics.SweepGradient;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import javax.crypto.Cipher;

/**
 * Created by wangc on 2017/7/13
 * E-MAIL:274281610@QQ.COM
 */

public class FirstBehavior extends CoordinatorLayout.Behavior<View> {

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }
}

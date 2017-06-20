package com.example.leo.adsafelike;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import java.lang.ref.WeakReference;

/**
 * Created by leo on 2017/5/31.
 */

public class RecycleViewBehavior extends CoordinatorLayout.Behavior<ViewPager>{

    private Context mContext;
    private WeakReference<View> mViewWeakReference;
    private Scroller scroller;
    private Handler handler;
    private boolean isScrolling;

    public RecycleViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        scroller = new Scroller(context);
        handler = new Handler();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ViewPager child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.vp) {
            mViewWeakReference = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, ViewPager child, int layoutDirection) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (layoutParams.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.getWidth(), (int) (parent.getHeight() - getDependencyViewCollpseHeight()));
            return true;
        }

        return super.onLayoutChild(parent, child, layoutDirection);
    }

    private float getDependencyViewCollpseHeight() {
        return ScreenUtils.getScalePxValue(mContext,getDependencyView().getResources().getDimension(R.dimen.collapsed_header_height));
    }

    private View getDependencyView() {
        return mViewWeakReference.get();
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ViewPager child, View dependency) {


       // float progress = 1.0f - Math.abs(dependency.getTranslationY() / (dependency.getHeight() - getDependencyViewCollpseHeight()));

        child.setY(dependency.getHeight() + dependency.getTranslationY());


       /* float scale = 1 + 0.4f * (1.0f - progress);
        dependency.setScaleX(scale);
        dependency.setScaleY(scale);*/



       /* CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) dependency.getLayoutParams();
        layoutParams.height = (int) (dependency.getHeight() + dependency.getTranslationY());
        dependency.setLayoutParams(layoutParams);*/

        //dependency.setAlpha(progress);
        return true;
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View directTargetChild, View target, int nestedScrollAxes) {
        System.out.println("leoyang onStartNestedScroll" );
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, ViewPager child, View directTargetChild, View target, int nestedScrollAxes) {
        System.out.println("leoyang onNestedScrollAccepted" );
        scroller.abortAnimation();
        isScrolling = false;

        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);


    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View target, int dx, int dy, int[] consumed) {
        System.out.println("leoyang onNestedPreScroll");
        View dependencyView = getDependencyView();

        float newTranslateY = dependencyView.getTranslationY() - dy;
        float minHeaderTranslate = -(dependencyView.getHeight() - getDependencyViewCollpseHeight());

        if (newTranslateY > minHeaderTranslate) {
            dependencyView.setTranslationY(newTranslateY);
            consumed[1] = dy;
        }



    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        View dependentView = getDependencyView();
        float newTranslateY = dependentView.getTranslationY() - dyUnconsumed;
        final float maxHeaderTranslate = 0;

        if (newTranslateY < maxHeaderTranslate) {
            dependentView.setTranslationY(newTranslateY);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ViewPager child, View target, float velocityX, float velocityY) {
        return onUserStopDragging(velocityY);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, ViewPager child, View target) {
        if (!isScrolling) {
            onUserStopDragging(800);
        }
    }

    private boolean onUserStopDragging(float velocity) {
        View dependentView = getDependencyView();
        float translateY = dependentView.getTranslationY();
        float minHeaderTranslate = -(dependentView.getHeight() - getDependencyViewCollpseHeight());

        if (translateY == 0 || translateY == minHeaderTranslate) {
            return false;
        }

        boolean targetState; // Flag indicates whether to expand the content.
        if (Math.abs(velocity) <= 800) {
            if (Math.abs(translateY) < Math.abs(translateY - minHeaderTranslate)) {
                targetState = false;
            } else {
                targetState = true;
            }
            velocity = 800; // Limit velocity's minimum value.
        } else {
            if (velocity > 0) {
                targetState = true;
            } else {
                targetState = false;
            }
        }

        float targetTranslateY = targetState ? minHeaderTranslate : 0;

        scroller.startScroll(0, (int) translateY, 0, (int) (targetTranslateY - translateY), (int) (1000000 / Math.abs(velocity)));
        handler.post(flingRunnable);
        isScrolling = true;
        return true;
    }

    private boolean isExpanded;
    private Runnable flingRunnable = new Runnable() {
        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                getDependencyView().setTranslationY(scroller.getCurrY());
                handler.post(this);
            } else {
                isExpanded = getDependencyView().getTranslationY() != 0;
                isScrolling = false;
            }
        }
    };
}

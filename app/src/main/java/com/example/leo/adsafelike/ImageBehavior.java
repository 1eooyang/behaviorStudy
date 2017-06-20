package com.example.leo.adsafelike;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by leo on 2017/5/31.
 */

public class ImageBehavior extends CoordinatorLayout.Behavior<ImageView> {

    private Context mContext;
    private ArgbEvaluator mArgbEvaluator;
    private WeakReference<View> mWeakReference;
    private float mStartYPosition;
    private float mStartOffset;
    private float mStartXPosition;
    private float mFinalXPosition;
    private float mFinalYPosition;
    private float mToolbarPosition;

    private boolean imageChange;
    private float final_size_x;
    private float final_size_y;
    private float init_size_x;
    private float init_size_y;

    public ImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {

        if (dependency != null && dependency.getId() == R.id.vp) {
            mWeakReference = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {

        init(child, dependency);
        //float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;


        float abs = Math.abs((mStartYPosition - dependency.getY()) / (mStartYPosition - mToolbarPosition));
        float progress = 1.f - abs;


        float translateY = mStartOffset * progress;


        if (mStartXPosition * progress <= mFinalXPosition) {
            child.setX(mFinalXPosition);
        } else {
            child.setX(mStartXPosition * progress);
        }

        if (translateY <= mFinalYPosition) {
            child.setY(mFinalYPosition);
        } else {
            child.setY(translateY);
        }

       /* if (progress < 0.2 && imageChange) {
            imageChange = false;
            child.setImageResource(R.drawable.logo);

        } else if (progress >= 0.2 && !imageChange) {
            imageChange = true;
            child.setImageResource(R.drawable.selector_image_logo);
        }
*/

        int size_x = (int) (final_size_x + (init_size_x - final_size_x) * progress);
        int size_y = (int) (final_size_y + (init_size_y - final_size_y) * progress);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();

        layoutParams.height = size_y;
        layoutParams.width = size_x;
        child.setLayoutParams(layoutParams);


        return true;

    }

    private void init(View child, View dependency) {
        if (mStartXPosition == 0)
            mStartXPosition = child.getX();
        if (mStartYPosition == 0)
            mStartYPosition = dependency.getY();
        if (mToolbarPosition == 0)
            mToolbarPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.collapsed_header_height));
        if (mFinalXPosition == 0) {
            mFinalXPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.imageview_margin_left));
        }

        if(mStartOffset == 0)
            mStartOffset =  ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.imageview_init_height));


        if (mFinalYPosition == 0) {
            mFinalYPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.imageview_margin_top));
        }

        if (final_size_x == 0)
            final_size_x = ScreenUtils.getScalePxValue(mContext, 50);
        if (final_size_y == 0)
            final_size_y = ScreenUtils.getScalePxValue(mContext, 50);
        if (init_size_x == 0)
            init_size_x = ScreenUtils.getScalePxValue(mContext, 274);
        if (init_size_y == 0)
            init_size_y = ScreenUtils.getScalePxValue(mContext, 302);

    }

    private View getDependencyView() {
        return mWeakReference.get();
    }


}

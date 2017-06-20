package com.example.leo.adsafelike;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Created by leo on 2017/5/31.
 */

public class ButtonBehavior extends CoordinatorLayout.Behavior<Button> {

    private Context mContext;
    private WeakReference<View> mWeakReference;
    private float mStartXPosition;
    private float mStartRightLocation;
    private float mToRightDiff;
    private float mFinalYPosition;
    private boolean ChangeBg;
    private float mStartTextSize;
    private float final_size_x;
    private float final_size_y;
    private float init_size_x;
    private float init_size_y;
    private float mStartOffset;
    private float mStartYPosition;
    private float mToolbarPosition;
    private float finalOffset;
    private float startOffset;

    public ButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.vp) {
            mWeakReference = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {

        init(child, dependency);

        //float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;
        Resources resources = getDependencyView().getResources();

        float abs = Math.abs((mStartYPosition - dependency.getY()) / (mStartYPosition - mToolbarPosition));
        float progress = 1.f - abs;


        float translateY = finalOffset + (startOffset - finalOffset) * progress;

     /*   if (mStartXPosition * progress <= dimension) {
            child.setX(dimension);
        } else {
            child.setX(mStartXPosition  * progress);
        }*/

        float v = mStartXPosition + (mToRightDiff * abs);
        child.setX(v);

        if (translateY <= mFinalYPosition) {
            // child.setBackgroundResource(R.drawable.open4);
            child.setY(mFinalYPosition);
        } else {
            // child.setBackgroundResource(R.drawable.open3);
            child.setY(translateY);
        }

        if (abs >= 0.8 && ChangeBg) {
            ChangeBg = false;
            child.setBackgroundResource(R.drawable.selector_switch_short);
        } else if (abs < 0.8 && !ChangeBg) {
            ChangeBg = true;
            child.setBackgroundResource(R.drawable.selector_switch_long);
        }

        child.setTextSize(mStartTextSize * progress);


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
            mStartXPosition = child.getX()+20;

        if (mStartRightLocation == 0)
            mStartRightLocation = mStartXPosition + child.getWidth();

        if (mToRightDiff == 0) {
            int width = dependency.getWidth();
            float smallWidth = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_final_size_w));
            float bigWidth = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_init_size_w));
            mToRightDiff = width - mStartRightLocation + bigWidth / 2 + smallWidth / 2;
        }

        if (mFinalYPosition == 0)
            mFinalYPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.botton_margin_top));

        if (mStartTextSize == 0)
            mStartTextSize = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.botton_text_size));

        if (final_size_x == 0)
            final_size_x = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_final_size_w));
        if (final_size_y == 0)
            final_size_y = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_final_size_h));
        if (init_size_x == 0)
            init_size_x = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_init_size_w));
        if (init_size_y == 0)
            init_size_y = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_init_size_h));
        if(mStartOffset == 0)
            mStartOffset =  ScreenUtils.getScalePxValue(mContext, 52);

        if (mStartYPosition == 0)
            mStartYPosition = dependency.getY();
        if (mToolbarPosition == 0)
            mToolbarPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.collapsed_header_height));


        if(finalOffset == 0)
        finalOffset = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_final_height));

        if(startOffset ==0)
         startOffset = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.button_init_height));

    }

    private View getDependencyView() {
        return mWeakReference.get();
    }


}

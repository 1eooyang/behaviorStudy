package com.example.leo.adsafelike;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by leo on 2017/5/31.
 */

public class TextViewBehavior extends CoordinatorLayout.Behavior<TextView> {

    private Context mContext;
    private WeakReference<View> mWeakReference;
    private float mStartXPosition;
    private final float mRealScale;
    private float mFinalXPosition;
    private float mFinalYPosition;
    private boolean textChange;
    private float mStartYPosition;
    private float mToolbarPosition;
    private float mStartOffset;

    public TextViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mRealScale = ScreenUtils.getRealScale(context);

    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.vp) {
            mWeakReference = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {

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

        if (progress == 1.f && !textChange) {
            textChange = true;
            String format = String.format("已保护您%s天\n今日已拦截%s条", "27", "108");
            child.setText(format);
        } else if (progress < 1.f && textChange) {
            textChange = false;
            child.setText(String.format("已保护您%s天", "27"));
        }


        //ScaleUtils.scaleViewAndChildren(child, mRealScale, 0);

        return true;

    }

    private void init(View child, View dependency) {
        if (mStartXPosition == 0)
            mStartXPosition = child.getX();


        if(mStartOffset == 0)
            mStartOffset =  ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.textview_init_height));


        if(mStartYPosition == 0)
            mStartYPosition = dependency.getY();

        if (mToolbarPosition == 0)
            mToolbarPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.collapsed_header_height));

        if (mFinalXPosition == 0) {
            mFinalXPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.textview_margin_left));
        }
        if (mFinalYPosition == 0) {
            mFinalYPosition = ScreenUtils.getScalePxValue(mContext, getDependencyView().getResources().getDimension(R.dimen.textview_margin_top));
        }

    }

    private View getDependencyView() {
        return mWeakReference.get();
    }


}

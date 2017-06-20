package com.example.leo.adsafelike;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 
 * @author xyb
 * 缩放工具类
 */
public class ScaleUtils {
	
	/**
     * 缩放View和它的子View
     */
    public static void scaleViewAndChildren(View view, float factor, int level) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        // 如果宽高是具体数值，则进行缩放。(MATCH_PARENT、WRAP_CONTENT 等都是负数)
        if(layoutParams.width > 0) {
            layoutParams.width *= factor;
        }
        if(layoutParams.height > 0) {
            layoutParams.height *= factor;
        }

        // 缩放margin
        if(layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams)layoutParams;
            marginParams.leftMargin *= factor;
            marginParams.topMargin *= factor;
            marginParams.rightMargin *= factor;
            marginParams.bottomMargin *= factor;
        }
        view.setLayoutParams(layoutParams);

        // EditText 有特殊的padding，不处理
        if(!(view instanceof EditText)) {
            // 缩放padding
            view.setPadding(
                    (int)(view.getPaddingLeft() * factor),
                    (int)(view.getPaddingTop() * factor),
                    (int)(view.getPaddingRight() * factor),
                    (int)(view.getPaddingBottom() * factor)
            );
        }

        // 缩放文字
        if(view instanceof TextView) {
        	scaleTextSize((TextView) view, factor);
        }
        
        // 如果是ViewGroup，继续缩放它的子View
        if(view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)view;
            for(int i = 0; i < vg.getChildCount(); i++) {
                scaleViewAndChildren(vg.getChildAt(i), factor, level + 1);
            }
        }
    }

    // 缩放文字
    public static void scaleTextSize(TextView tv, float factor) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv.getTextSize() * factor);
    }
}

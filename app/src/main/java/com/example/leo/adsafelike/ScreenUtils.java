package com.example.leo.adsafelike;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 * 
 * @author zhy
 * 
 */
public final class ScreenUtils
{
	private ScreenUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}


	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}
	public static int pxConvert(Context context, float pxValue)
	{
		int scheight = getScreenHeight(context);// 屏幕高度
		int scwight = getScreenWidth(context);// 屏幕宽度
		float scal = (float) scwight / (float) scheight;// 屏幕宽高比例
		float scaling;// 需要缩放比例
		// 0.526为720/1280的值
		if (scal >= 0.526) {
			scaling = (float) scheight / px2dp(context, 1280);
		} else {
			scaling = (float) scwight / px2dp(context, 720);
		}
		
		return (int) (px2dp(context, pxValue) * scaling + 0.5f);
	}
	
	public static float getRealScale(Context context)
	{
		int height = getScreenHeight(context);// 屏幕高度
		int width = getScreenWidth(context);// 屏幕宽度
		float scal = (float) width / (float) height;// 屏幕宽高比例
		float scale;// 需要缩放比例
		// 0.526为720/1280的值
		if (scal >= 0.526) {
			scale = (float) height / 1280;
		} else {
			scale = (float) width / 720;
		}

		return scale;
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static float px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return pxValue / scale + 0.5f;
	}

	public static int dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static float getScalePxValue(Context context, float value) {
		if (value <= 4) {
			return value;
		}
		return  (float) Math.ceil(getRealScale(context) * value);
	}
}

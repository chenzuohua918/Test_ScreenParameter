package com.example.test_screenparameter;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtil {
	/**
	 * 获取屏幕英寸数
	 * 
	 * @param context
	 * @return
	 */
	public static double getScreenInch(Activity context) {
		double mInch = 0;

		if (mInch != 0.0d) {
			return mInch;
		}

		try {
			int realWidth = 0, realHeight = 0;
			Display display = context.getWindowManager().getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);
			if (android.os.Build.VERSION.SDK_INT >= 17) {
				Point size = new Point();
				display.getRealSize(size);
				realWidth = size.x;
				realHeight = size.y;
			} else if (android.os.Build.VERSION.SDK_INT < 17
					&& android.os.Build.VERSION.SDK_INT >= 14) {
				Method mGetRawH = Display.class.getMethod("getRawHeight");
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				realWidth = (Integer) mGetRawW.invoke(display);
				realHeight = (Integer) mGetRawH.invoke(display);
			} else {
				realWidth = metrics.widthPixels;
				realHeight = metrics.heightPixels;
			}

			mInch = formatDouble(
					Math.sqrt((realWidth / metrics.xdpi)
							* (realWidth / metrics.xdpi)
							+ (realHeight / metrics.ydpi)
							* (realHeight / metrics.ydpi)), 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mInch;
	}

	/**
	 * Double类型保留指定位数的小数，返回double类型（四舍五入） newScale 为指定的位数
	 */
	private static double formatDouble(double d, int newScale) {
		BigDecimal bd = new BigDecimal(d);
		return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 获取分辨率
	 * 
	 * @param context
	 * @return
	 */
	public static String getScreenResolution(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = (int) (metrics.widthPixels * metrics.density);
		int height = (int) (metrics.heightPixels * metrics.density);
		return width + " X " + (height + getStatusHeight(context));
	}

	/**
	 * 状态栏高度
	 * 
	 * @param activity
	 * @return
	 */
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}

	public static float getScreenPPI(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		double ppi = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2))
				/ getScreenInch(context);
		BigDecimal bd = new BigDecimal(ppi);
		// 四舍五入，保留一位小数
		return bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}

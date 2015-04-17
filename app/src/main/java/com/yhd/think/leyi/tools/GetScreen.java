package com.yhd.think.leyi.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class GetScreen {

	public static int getScreenWidth(Context context){
		Display mDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mDisplay.getMetrics(new DisplayMetrics());
		int width = mDisplay.getWidth();
		return width;
	}
	
	public static int getScreenHeight(Context context){
		Display mDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mDisplay.getMetrics(new DisplayMetrics());
		int height = mDisplay.getHeight();
		return height;
	}
	
}

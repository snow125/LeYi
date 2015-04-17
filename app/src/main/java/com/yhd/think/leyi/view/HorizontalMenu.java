package com.yhd.think.leyi.view;

import com.nineoldandroids.view.ViewHelper;
import com.yhd.think.leyi.fragment.GoodsFragment;
import com.yhd.think.leyi.fragment.ServeceFragment;
import com.yhd.think.leyi.tools.DensityTool;
import com.yhd.think.leyi.tools.GetScreen;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class HorizontalMenu extends HorizontalScrollView {

	private LinearLayout mLinearLayout;
	private ViewGroup myMenu;
	private int width;
	private int menuWdith;
	private int totalWdith;
	private ViewGroup myContent;
	private int contentWdith;
	private boolean once;
	private boolean isOpen;
    private GestureDetector mGestureDetector;
	
	public HorizontalMenu(Context context) {
		super(context);
        init(context);
	}

	public HorizontalMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init(context);
	}

	public HorizontalMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
        init(context);
	}

    private void init(Context context) {
        width = GetScreen.getScreenWidth(context);
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        return (Math.abs(distanceY) < Math.abs(distanceX));
                    }
                });
    }


    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!once){
			mLinearLayout = (LinearLayout) getChildAt(0);
			myMenu = (ViewGroup) mLinearLayout.getChildAt(0);
			myContent = (ViewGroup) mLinearLayout.getChildAt(1);

            /*myContent.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    closeMenu();
                }
            });*/

			myMenu.getLayoutParams().width = width;
			myContent.getLayoutParams().width = width;
			totalWdith = myMenu.getLayoutParams().width = width-100;
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed){
			scrollTo(totalWdith, 0);
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
        if(GoodsFragment.sortPop!=null && GoodsFragment.sortPop.isShowing()){
            GoodsFragment.sortPop.dismiss();
        }
        if(ServeceFragment.sortPop!=null && ServeceFragment.sortPop.isShowing()){
            ServeceFragment.sortPop.dismiss();
        }
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX>=totalWdith/2){
                    smoothScrollTo(totalWdith, 0);
                    isOpen = false;
                }else{
                    smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
        }
		return super.onTouchEvent(ev);
	}

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    public boolean isOpenMenu(){
        return isOpen;
    }

    public void switchMenu(){
		if(isOpen){
			smoothScrollTo(totalWdith, 0);
		}else{
			smoothScrollTo(0, 0);
		}
		isOpen = !isOpen;
	}

    public void closeMenu(){
        if(isOpen){
            closeMenuAfterjudge();
        }
    }

    public void closeMenuAfterjudge(){
        smoothScrollTo(totalWdith, 0);
        isOpen = !isOpen;
    }

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		
		float translate = l * 1.0f/totalWdith;
		ViewHelper.setTranslationX(myMenu, totalWdith * translate * 0.7f);
		
		float alpha = 0.5f + 0.5f * (1-translate);
		ViewHelper.setAlpha(myMenu, alpha);
		
		float size = 0.7f + 0.3f * (1-translate);
		ViewHelper.setScaleX(myMenu, size);
		ViewHelper.setScaleY(myMenu, size);
		
		float rightScale = 0.7f + 0.3f * translate;
		ViewHelper.setPivotX(myContent, 0);
		ViewHelper.setPivotY(myContent, myContent.getHeight()/2);
		ViewHelper.setScaleX(myContent, rightScale);
		ViewHelper.setScaleY(myContent, rightScale);
	}
	
}

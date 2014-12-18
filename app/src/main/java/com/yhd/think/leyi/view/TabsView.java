package com.yhd.think.leyi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.yhd.think.leyi.R;


/**
 * @author snow
 */
public class TabsView extends View {

    private Context context;

    private int tabCount;
    private String[] tabNames;
    private String[] tabPictures;

    public TabsView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TabsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TabsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.context = context;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TabsView, defStyle, 0);
        tabCount = a.getInt(R.styleable.TabsView_tabCount, 0);
        tabNames = a.getResources().getStringArray(R.array.tab_names);
        tabPictures = a.getResources().getStringArray(R.array.tab_picture);
        Log.e("123", "-----"+tabCount);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

    }
}

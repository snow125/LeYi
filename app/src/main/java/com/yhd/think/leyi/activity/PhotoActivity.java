package com.yhd.think.leyi.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Image;
import com.yhd.think.leyi.fragment.LoopViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends FragmentActivity {

    public static List<Image> images = new ArrayList<Image>();
    public static int type;
    public static boolean isAuto;

    private LoopViewPagerFragment mPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);
        initImages();
        initFragment();
    }

    private void initFragment() {
        LoopViewPagerFragment.setInfo(images, type, false);
        mPagerFragment = new LoopViewPagerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_photo_picture, mPagerFragment)
                .commit();
        mPagerFragment.setUse(false);
    }

    private void initImages() {
        if(images == null){
            throw new NullPointerException("images is null");
        }
    }

    public static void setInfo(List<Image> _images, int _type, boolean _isAuto){
        images = _images;
        type = _type;
        isAuto = _isAuto;
    }

}

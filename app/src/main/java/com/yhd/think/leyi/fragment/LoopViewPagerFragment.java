package com.yhd.think.leyi.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.PhotoActivity;
import com.yhd.think.leyi.data.Image;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.DensityTool;

import java.util.ArrayList;
import java.util.List;

import imbryk.viewPager.LoopViewPager;

/**
 * @author snow
 *
 */
public class LoopViewPagerFragment extends Fragment {

    public final static int TYPE_URL = 0;
    public final static int TYPE_BITMAP = 1;
    private static int type;
    private View rootView;
    private LoopViewPager mLoopViewPager;
    private ImageView[] indicates;
    private LinearLayout indicate_ll;
    public static List<Image> images = new ArrayList<Image>();
    private boolean mIsDragging;
    private int now;
    public static boolean isAuto;
    private boolean isUse = true;
    private NetworkImageView picture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_loop_view_pager, container, false);
        initImages();
        initIndicates();
        initViewPager();
        if(isAuto){
            autoView();
        }
        return rootView;
    }

    private void initImages() {
        if(images == null){
            throw new NullPointerException("images is null");
        }
    }

    private void initViewPager() {
        mLoopViewPager = (LoopViewPager) rootView.findViewById(R.id.activity_detail_viewpager);
        picture = (NetworkImageView) rootView.findViewById(R.id.one_size_view);
        if(images.size() == 1){
            mLoopViewPager.setVisibility(View.GONE);
            picture.setVisibility(View.VISIBLE);
            picture.setImageUrl(images.get(0).getUrl(), ImageCacheManager.getInstance().getImageLoader());
            picture.setErrorImageResId(R.drawable.photo_wrong);
            picture.setDefaultImageResId(R.drawable.content_loading_large);
            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isUse){
                        PhotoActivity.setInfo(images, type, isAuto);
                        Intent i = new Intent(getActivity(), PhotoActivity.class);
                        startActivity(i);
                    }
                }
            });
        }else{
            mLoopViewPager.setVisibility(View.VISIBLE);
            picture.setVisibility(View.GONE);
            mLoopViewPager.setAdapter(new ImagePagerAdapter(images, getActivity(), type));
            mLoopViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i2) {

                }

                @Override
                public void onPageSelected(int i) {
                    changeIndicateColor(i);
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (ViewPager.SCROLL_STATE_DRAGGING == i) {
                        mIsDragging = true;
                    } else {
                        mIsDragging = false;
                    }
                }
            });
            initPager();
        }

    }

    private void initPager() {
        mLoopViewPager.setCurrentItem(0);
        changeIndicate(0);
    }

    private void changeIndicate(int a){
        for (int i=0;i<images.size();i++){
            if(i==a){
                indicates[i].setBackgroundColor(Color.RED);
            }else{
                indicates[i].setBackgroundColor(Color.BLACK);
            }
        }
    }


    private void changeIndicateColor(int position){
        changeIndicate(position);
    }

    private void initIndicates() {
        indicate_ll = (LinearLayout) rootView.findViewById(R.id.activity_detail_indicate);
        indicates = new ImageView[images.size()];
        for(int i=0;i<images.size();i++){
            addImageView(i);
        }
    }

    private void autoView(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                if(!mIsDragging){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    now = mLoopViewPager.getCurrentItem();
                    mLoopViewPager.setCurrentItem(++now);
                    autoView();
                    break;
            }
        }
    };

    private void addImageView(int i) {
        indicates[i] = new ImageView(getActivity());
        indicates[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(
                DensityTool.dip2px(getActivity(), 5),DensityTool.dip2px(getActivity(),5));
        para.setMargins(0,0,DensityTool.dip2px(getActivity(),10),0);
        indicates[i].setLayoutParams(para);
        indicate_ll.addView(indicates[i]);
    }

    public static void setInfo(List<Image> _images, int _type, boolean _isAuto){
        images = _images;
        type = _type;
        isAuto = _isAuto;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        this.isUse = use;
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private List<Image> images;
        private NetworkImageView[] views;
        private Context context;
        private int type = TYPE_URL;

        /*
          可以不要这些参数？
        */
        public ImagePagerAdapter(List<Image> images, Context context, int type) {
            this.images = images;
            this.context = context;
            views = new NetworkImageView[images.size()];
            this.type = type;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            if(views[position]==null){
                NetworkImageView image = new NetworkImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                image.setLayoutParams(params);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if(type == TYPE_URL){
                    image.setImageUrl(images.get(position).getUrl(), ImageCacheManager.getInstance().getImageLoader());
                    image.setErrorImageResId(R.drawable.photo_wrong);
                    image.setDefaultImageResId(R.drawable.content_loading_large);
                }else if(type == TYPE_BITMAP){
                    /*Log.e("123", "this");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(images.get(position).getBitmap());
                        }
                    }, 100);*/
                }
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isUse){
                            PhotoActivity.setInfo(images, type, isAuto);
                            Intent i = new Intent(getActivity(), PhotoActivity.class);
                            startActivity(i);
                        }
                    }
                });
                views[position] = image;
            }
            container.removeView(views[position]);
            container.addView(views[position]);
            return views[position];
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            /**
             * public void destroyItem(View container, int position, Object object) {
             *      throw new UnsupportedOperationException("Required method destroyItem was not overridden");
             * }
             * 删除super
             */
            //super.destroyItem(container, position, object);
        }
    }

}

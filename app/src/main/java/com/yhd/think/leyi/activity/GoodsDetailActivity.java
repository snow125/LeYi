package com.yhd.think.leyi.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.adapter.GoodCommentAdapter;
import com.yhd.think.leyi.data.Comment;
import com.yhd.think.leyi.data.GoodsDetail;
import com.yhd.think.leyi.data.Image;

import java.util.ArrayList;
import java.util.List;

import imbryk.viewPager.LoopViewPager;

import static com.yhd.think.leyi.R.id.owner_words;

public class GoodsDetailActivity extends BaseActivity {

    private LoopViewPager mLoopViewPager;
    private List<Image> images = new ArrayList<Image>();
    private ImageView vIndicateL, vIndicateC, vIndicateR;
    private ImageView back;
    private TextView goodName;
    private TextView goodDecription;
    private TextView goodRequest;
    private ImageView goodShare;
    private GoodsDetail good;
    private View ownerPic;
    private TextView ownerName;
    private TextView ownerWords;
    private ImageView ownerTel;
    private ImageView ownerMessage;
    private TextView watchComment;
    private ListView comments;
    private boolean isSeeComment = true;
    private boolean mIsDragging;
    private int now;
    private GoodCommentAdapter mGoodCommentAdapter;
    private List<Comment> ownerComments = new ArrayList<Comment>();
    private ImageView wantComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        findViews();
        initData();
        initComments();
        setViews();
        initViewPager();
        autoView();
    }

    private void initComments() {
        ownerComments.clear();

        Comment mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);

        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);

        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);

        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        ownerComments.add(mComment);
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

    private void initData(){
        images.add(new Image(R.drawable.default_pic));
        images.add(new Image(R.drawable.ic_launcher));
        images.add(new Image(R.drawable.default_pic));

        Bundle b = getIntent().getExtras();
        String goods_name = (String) b.get("name");
        String goods_request = (String) b.get("request");

        good = new GoodsDetail("snow","换个东西吧","11000221234",goods_request,"两性磨合剂",goods_name,R.drawable.ic_launcher,R.drawable.default_pic);
    }

    private void findViews(){
        vIndicateL = (ImageView) findViewById(R.id.activity_detail_viewpager_pic1);
        vIndicateC = (ImageView) findViewById(R.id.activity_detail_viewpager_pic2);
        vIndicateR = (ImageView) findViewById(R.id.activity_detail_viewpager_pic3);

        goodName = (TextView) findViewById(R.id.goods_name);
        goodDecription = (TextView) findViewById(R.id.goods_words);
        goodShare = (ImageView) findViewById(R.id.goods_share);
        goodRequest = (TextView) findViewById(R.id.goods_request);
        ownerPic = findViewById(R.id.owner_picture);
        ownerName = (TextView) findViewById(R.id.owner_name);
        ownerWords = (TextView) findViewById(R.id.owner_words);
        ownerTel = (ImageView) findViewById(R.id.onwer_tel);
        ownerMessage = (ImageView) findViewById(R.id.onwer_message);
        watchComment = (TextView) findViewById(R.id.activity_detail_watch_comments);
        comments = (ListView) findViewById(R.id.activity_detail_listview_comment);
        wantComment = (ImageView) findViewById(R.id.goods_want_comment);

        back = (ImageView) findViewById(R.id.activity_detail_back);
    }

    private void setViews(){
        goodName.setText(good.getName());
        goodRequest.setText(good.getHopeMemo());
        goodDecription.setText(good.getDescription());
        ownerPic.setBackgroundResource(good.getOwnerImageId());
        ownerName.setText(good.getOwnerName());
        ownerWords.setText(good.getOwnerSign());
        goodShare .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ownerTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ownerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        watchComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSeeComment){
                    comments.setVisibility(View.VISIBLE);
                    watchComment.setText("隐藏评论");
                }else{
                    comments.setVisibility(View.GONE);
                    watchComment.setText("查看评论");
                }
                isSeeComment = !isSeeComment;
            }
        });
        wantComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mGoodCommentAdapter = new GoodCommentAdapter(this,ownerComments);
        comments.setAdapter(mGoodCommentAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewPager() {
        mLoopViewPager = (LoopViewPager) findViewById(R.id.activity_detail_viewpager);
        mLoopViewPager.setAdapter(new MyPagerAdapter(images, GoodsDetailActivity.this));
        mLoopViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                changeIndicate(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (ViewPager.SCROLL_STATE_DRAGGING==i) {
                    mIsDragging=true;
                }else {
                    mIsDragging=false;
                }
            }
        });
        initPager();
    }

    private void initPager() {
        mLoopViewPager.setCurrentItem(0);
        vIndicateL.setBackgroundColor(Color.RED);
        vIndicateC.setBackgroundColor(Color.BLACK);
        vIndicateR.setBackgroundColor(Color.BLACK);
    }


    private void changeIndicate(int position){
        switch (position){
            case 0:
                vIndicateL.setBackgroundColor(Color.RED);
                vIndicateC.setBackgroundColor(Color.BLACK);
                vIndicateR.setBackgroundColor(Color.BLACK);
                break;
            case 1:
                vIndicateL.setBackgroundColor(Color.BLACK);
                vIndicateC.setBackgroundColor(Color.RED);
                vIndicateR.setBackgroundColor(Color.BLACK);
                break;
            case 2:
                vIndicateL.setBackgroundColor(Color.BLACK);
                vIndicateC.setBackgroundColor(Color.BLACK);
                vIndicateR.setBackgroundColor(Color.RED);
                break;
        }
    }

    private class MyPagerAdapter extends PagerAdapter{

        private List<Image> images;
        private ImageView[] views;
        private Context context;

        private MyPagerAdapter(List<Image> images, Context context) {
            this.images = images;
            this.context = context;
            views = new ImageView[images.size()];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(views[position]==null){
                ImageView image = new ImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                image.setLayoutParams(params);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image.setBackgroundResource(images.get(position).getResId());
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

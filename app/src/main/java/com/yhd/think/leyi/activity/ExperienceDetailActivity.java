package com.yhd.think.leyi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.adapter.ExperienceAdapter;
import com.yhd.think.leyi.adapter.GoodCommentAdapter;
import com.yhd.think.leyi.data.Comment;
import com.yhd.think.leyi.data.ExperienceDetail;
import com.yhd.think.leyi.view.ListViewInScroll;

import java.util.ArrayList;
import java.util.List;

public class ExperienceDetailActivity extends BaseActivity {

    private ImageView back_iv;
    private ImageView share_iv;
    private ImageView sellGood;
    private ImageView sellHead;
    private ImageView shopGood;
    private ImageView shopHead;
    private TextView sellWord;
    private TextView shopWord;
    //private ListViewInScroll mListView;
    private ListView mListView;
    private List<Comment> experienceComments = new ArrayList<Comment>();
    private GoodCommentAdapter adapter;

    //private String sellGoodUrl;
    //private String shopGoodUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);
        findViews();
        initInfo();
        setListeners();
        initComments();
        initListView();
    }

    private void initComments() {
        experienceComments.clear();
        Comment mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
        mComment = new Comment("小新","这个不错啊",R.drawable.default_pic);
        experienceComments.add(mComment);
    }

    private void initListView() {
        mListView = (/*ListViewInScroll*/ListView) findViewById(R.id.activity_experience_listview_comment);
        adapter = new GoodCommentAdapter(this, experienceComments);
        mListView.setAdapter(adapter);
        //mListView.setEnabled(false);
        mListView.setItemsCanFocus(false);
    }

    private void initInfo() {
        Bundle b = getIntent().getExtras();
        int sellGoodID = (Integer)b.get("sell_good_url");
        int shopGoodID = (Integer)b.get("shop_good_url");

        ExperienceDetail experience = new ExperienceDetail(shopGoodID,sellGoodID,R.drawable.default_pic,R.drawable.default_pic, "你好", "哈哈");

        sellGood.setBackgroundResource(experience.getSellGoodID());
        sellHead.setBackgroundResource(experience.getSellImageID());
        shopGood.setBackgroundResource(experience.getShopGoodID());
        shopHead.setBackgroundResource(experience.getShopImageID());
        sellWord.setText(experience.getSellWord());
        shopWord.setText(experience.getShopWord());
    }

    private void setListeners() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        share_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void findViews() {
        back_iv = (ImageView) findViewById(R.id.actionbar_back);
        share_iv = (ImageView) findViewById(R.id.activity_experience_share);
        sellGood = (ImageView) findViewById(R.id.activity_experience_sellgood);
        sellHead = (ImageView) findViewById(R.id.activity_experience_sellhead);
        shopGood = (ImageView) findViewById(R.id.activity_experience_shopgood);
        shopHead = (ImageView) findViewById(R.id.activity_experience_shophead);
        sellWord = (TextView) findViewById(R.id.activity_experience_sellword);
        shopWord = (TextView) findViewById(R.id.activity_experience_shopword);
    }

}

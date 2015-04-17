package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.adapter.GoodsAdapter;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Goods;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ImageView back;
    private ListView sellList;
    private GoodsAdapter adapter;
    private List<Goods> mGoods = new ArrayList<Goods>();
    private SwipeRefreshLayout swipeLayout;
    private TextView title_tv;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_list);
        initData();
        findViews();
        initBundle();
        setViews();
        initListView();
    }

    private void initBundle() {
        Bundle b = getIntent().getExtras();
        title = (String)b.get("title");
        title_tv.setText(title);
    }

    private void initListView() {
        sellList = (ListView) findViewById(R.id.activity_sell_listview);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        adapter = new GoodsAdapter(this, mGoods, Constants.TYPE_NULL);
        sellList.setAdapter(adapter);
        sellList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListActivity.this, GoodsDetailActivity.class);
                i.putExtra("name",mGoods.get(position).getName());
                i.putExtra("request",mGoods.get(position).getRequest());
                startActivity(i);
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void setViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        back = (ImageView) findViewById(R.id.actionbar_back);
        title_tv = (TextView) findViewById(R.id.activity_list_title);
    }

    private void initData() {
        mGoods.clear();
        Goods good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
        good = new Goods("高数","两只女朋友", R.drawable.default_pic);
        mGoods.add(good);
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }

}

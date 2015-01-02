package com.yhd.think.leyi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.GoodsDetailActivity;
import com.yhd.think.leyi.adapter.GoodsAdapter;
import com.yhd.think.leyi.data.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author snow
 *
 */
public class GoodsFragment extends BaseFragment {

    private View rootView;
    private ListView mListView;
    private GoodsAdapter adapter;
    private List<Goods> mGoods = new ArrayList<Goods>();
    private View selector;
    private View search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_goods, container, false);
       initData();
       initListView();
       initViews();
       return rootView;
    }

    private void initViews() {
        selector = rootView.findViewById(R.id.actionbar_selector);
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        search = rootView.findViewById(R.id.actionbar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private void initListView() {
        mListView = (ListView) rootView.findViewById(R.id.fragment_goods_listview);
        adapter = new GoodsAdapter(getActivity(), mGoods);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), GoodsDetailActivity.class);
                i.putExtra("name",mGoods.get(position).getName());
                i.putExtra("request",mGoods.get(position).getRequest());
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GoodsFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("GoodsFragment");
    }

}

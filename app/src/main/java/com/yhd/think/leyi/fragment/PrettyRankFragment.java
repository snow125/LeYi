package com.yhd.think.leyi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.ExperienceDetailActivity;
import com.yhd.think.leyi.activity.GoodsDetailActivity;
import com.yhd.think.leyi.adapter.ExperienceAdapter;
import com.yhd.think.leyi.adapter.GoodsAdapter;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.Experience;
import com.yhd.think.leyi.data.Goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author snow
 *
 */
public class PrettyRankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private ListView mListView;
    private ExperienceAdapter adapter;
    private List<Experience> mExperiences = new ArrayList<Experience>();
    private SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pretty_rank, container, false);
        initData();
        initListView();
        return rootView;
    }

    private void initData() {
        mExperiences.clear();
        Experience experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);
        experience = new Experience(R.drawable.default_pic, R.drawable.default_pic, 100, new Date(2015,1,26));
        mExperiences.add(experience);

    }

    private void initListView() {
        mListView = (ListView) rootView.findViewById(R.id.fragment_pretty_rank_listview);
        adapter = new ExperienceAdapter(getActivity(), mExperiences, Constants.TYPE_PRETTY);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ExperienceDetailActivity.class);
                i.putExtra("sell_good_url",mExperiences.get(position).getSellGoodID());
                i.putExtra("shop_good_url",mExperiences.get(position).getShopGoodID());
                startActivity(i);
            }
        });
        adapter.notifyDataSetChanged();
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PrettyRankFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PrettyRankFragment");
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        swipeLayout.setRefreshing(false);
    }

}

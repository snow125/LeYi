package com.yhd.think.leyi.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.PublishActivity;

/**
 *
 * @author snow
 *
 */
public class PublishFragment extends BaseFragment {

    private View rootView;
    private ImageView sell;
    private ImageView shop;
    private ImageView sellBook;
    private ImageView shopBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_publish, container, false);
        findViews();
        setLiseners();
        return rootView;
    }

    private void setLiseners() {
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct("卖",true,false);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct("买",false,false);
            }
        });
        sellBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct("卖书",true,true);
            }
        });
        shopBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct("买书",false,true);
            }
        });
    }

    private void startAct(String title, boolean isSell, boolean isBook){
        Intent i = new Intent(getActivity(), PublishActivity.class);
        i.putExtra("isBook",isBook);
        i.putExtra("isSell",isSell);
        i.putExtra("title",title);
        startActivity(i);
    }

    private void findViews() {
        sell = (ImageView) rootView.findViewById(R.id.fragment_publish_sell);
        shop = (ImageView) rootView.findViewById(R.id.fragment_publish_shop);
        sellBook = (ImageView) rootView.findViewById(R.id.fragment_publish_sell_book);
        shopBook = (ImageView) rootView.findViewById(R.id.fragment_publish_shop_book);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PublishFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PublishFragment");
    }

}

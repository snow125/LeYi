package com.yhd.think.leyi.fragment;



import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.LoginActivity;
import com.yhd.think.leyi.data.User;

/**
 * @author snow
 *
 */
public class MineFragment extends BaseFragment {

    private User user = User.getInstance();
    private View rootView;
    private ImageView face_iv;
    private TextView word_tv;
    private TextView sell_list;
    private TextView shop_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        findViews();
        setViews();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViews() {
        if(user.isLogin()){
            face_iv.setBackground(new BitmapDrawable(user.getFace()));
            word_tv.setText(user.getWords());
            word_tv.setVisibility(View.VISIBLE);
        }else{
            face_iv.setBackgroundResource(R.drawable.ic_launcher);
            word_tv.setVisibility(View.GONE);
        }
        face_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(i, LoginActivity.SUCCESS);
            }
        });
        sell_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        shop_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LoginActivity.SUCCESS){
            if(resultCode == LoginActivity.SUCCESS){

            }
        }
    }

    private void findViews() {
        face_iv = (ImageView) rootView.findViewById(R.id.fragment_mine_face);
        word_tv = (TextView) rootView.findViewById(R.id.fragment_mine_word);
        sell_list = (TextView) rootView.findViewById(R.id.fragment_mine_sell);
        shop_list = (TextView) rootView.findViewById(R.id.fragment_mine_shop);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MineFragment");
    }

}

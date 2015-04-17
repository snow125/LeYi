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
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.view.NetworkCircleImageView;

/**
 *
 * @author snow
 *
 */
public class PublishFragment extends BaseFragment {

    private static User user = User.getInstance();
    private View rootView;
    private ImageView sell;
    private ImageView shop;
    private static NetworkCircleImageView menu;

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
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalMenu.switchMenu();
            }
        });
        updateInfo();
    }

    private void startAct(String title, boolean isSell, boolean isBook){
        Intent i = new Intent(getActivity(), PublishActivity.class);
        i.putExtra("isSell",isSell);
        i.putExtra("title",title);
        startActivity(i);
    }

    private void findViews() {
        menu = (NetworkCircleImageView) rootView.findViewById(R.id.actionbar_menu);
        sell = (ImageView) rootView.findViewById(R.id.fragment_publish_sell);
        shop = (ImageView) rootView.findViewById(R.id.fragment_publish_shop);
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

    public static void updateInfo(){
        if(user.isLogin()){
            if(menu!=null){
                menu.setImageUrl(user.getFaceUrl(), ImageCacheManager.getInstance().getImageLoader());
                menu.setErrorImageResId(R.drawable.anonymous_icon);
                menu.setDefaultImageResId(R.drawable.anonymous_icon);
            }
        }else{
            if(menu!=null){
                menu.setImageUrl(null, ImageCacheManager.getInstance().getImageLoader());
                menu.setErrorImageResId(R.drawable.anonymous_icon);
                menu.setDefaultImageResId(R.drawable.anonymous_icon);
            }
        }
    }

}

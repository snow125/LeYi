package com.yhd.think.leyi.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.view.NetworkCircleImageView;

/**
 * @author snow
 *
 */
public class RankFragment extends BaseFragment {

    private static User user = User.getInstance();
    private View rootView;
    private RelativeLayout time;
    private RelativeLayout pretty;
    private FragmentManager fm;
    private TimeRankFragment mTimeRankFragment;
    private PrettyRankFragment mPrettyRankFragment;
    private static NetworkCircleImageView menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_rank, container, false);
        findViews();
        //fm = getActivity().getSupportFragmentManager();
        fm = this.getChildFragmentManager();
        initFragments();
        setViews();
        return rootView;
    }

    private void initFragments() {
        mTimeRankFragment = new TimeRankFragment();
        mPrettyRankFragment = new PrettyRankFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_rank_fragments,mPrettyRankFragment)
                .commit();
    }

    private void setViews() {
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .replace(R.id.fragment_rank_fragments, mTimeRankFragment)
                        .commit();
            }
        });
        pretty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .replace(R.id.fragment_rank_fragments,mPrettyRankFragment)
                        .commit();
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

    private void findViews() {
        menu = (NetworkCircleImageView) rootView.findViewById(R.id.actionbar_menu);
        time = (RelativeLayout) rootView.findViewById(R.id.fragment_rank_time);
        pretty = (RelativeLayout) rootView.findViewById(R.id.fragment_rank_pretty);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RankFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RankFragment");
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

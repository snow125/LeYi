package com.yhd.think.leyi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.fragment.BaseFragment;
import com.yhd.think.leyi.fragment.GoodsFragment;
import com.yhd.think.leyi.fragment.PublishFragment;
import com.yhd.think.leyi.fragment.RankFragment;
import com.yhd.think.leyi.fragment.ServeceFragment;
import com.yhd.think.leyi.fragment.SettingFragment;
import com.yhd.think.leyi.fragment.TabsFragment;
import com.yhd.think.leyi.view.HorizontalMenu;


public class MainActivity extends BaseActivity implements TabsFragment.TabClickListener{

    private HorizontalMenu horizontalMenu;
    private RelativeLayout mContent;
    private SettingFragment mSettingFragment;
    private BaseFragment[] fragments = new BaseFragment[TabsFragment.FRAGMENT_COUNT];
    private TabsFragment mTabsFragment = new TabsFragment(new TabsFragment.TabFactory() {
        @Override
        public BaseFragment newInstant(int index) {
            switch (index){
                case TabsFragment.FRAGMENT_FIRST:
                    fragments[index] = new GoodsFragment();
                    break;
                case TabsFragment.FRAGMENT_THIRD:
                    fragments[index] = new PublishFragment();
                    break;
                case TabsFragment.FRAGMENT_SECOND:
                    fragments[index] = new ServeceFragment();
                    break;
                case TabsFragment.FRAGMENT_FOURTH:
                    fragments[index] = new RankFragment();
                    break;
                case TabsFragment.FRAGMENT_FIVETH:
                    fragments[index] = new RankFragment();
                    break;
            }
            return fragments[index];
        }
    }, R.id.activity_base_fragments);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initFragment();
        setViews();
    }

    private void setViews() {
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horizontalMenu.closeMenu();
            }
        });
    }

    private void findViews() {
        horizontalMenu = (HorizontalMenu) findViewById(R.id.activity_horizon_menu);
        mContent = (RelativeLayout) findViewById(R.id.activity_content);
    }

    private void initFragment() {
        mTabsFragment.setmListener(this);
        mSettingFragment = new SettingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_tab_fragments, mTabsFragment)
                .replace(R.id.activity_menu, mSettingFragment)
                .commit();
    }

    public HorizontalMenu getHorizontalMenu() {
        return horizontalMenu;
    }

    public void setHorizontalMenu(HorizontalMenu horizontalMenu) {
        this.horizontalMenu = horizontalMenu;
    }

    @Override
    public void onBackPressed() {
        if(GoodsFragment.sortPop.isShowing()){
            GoodsFragment.sortPop.dismiss();
        }else if(ServeceFragment.sortPop!=null && ServeceFragment.sortPop.isShowing()){
            ServeceFragment.sortPop.dismiss();
        }else if(horizontalMenu.isOpenMenu()){
            horizontalMenu.closeMenuAfterjudge();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(int index) {
        if(GoodsFragment.sortPop!=null && GoodsFragment.sortPop.isShowing()){
            GoodsFragment.sortPop.dismiss();
        }
        if(ServeceFragment.sortPop!=null && ServeceFragment.sortPop.isShowing()){
            ServeceFragment.sortPop.dismiss();
        }
        mTabsFragment.showFragment(index);
    }
}

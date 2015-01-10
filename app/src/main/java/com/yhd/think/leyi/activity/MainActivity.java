package com.yhd.think.leyi.activity;

import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.fragment.BaseFragment;
import com.yhd.think.leyi.fragment.GoodsFragment;
import com.yhd.think.leyi.fragment.MineFragment;
import com.yhd.think.leyi.fragment.PublishFragment;
import com.yhd.think.leyi.fragment.RankFragment;
import com.yhd.think.leyi.fragment.SettingFragment;
import com.yhd.think.leyi.fragment.TabsFragment;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;


public class MainActivity extends BaseActivity {

    public static final int FRAGMENT_GOODS =  0;
    public static final int FRAGMENT_PUBLISH =  1;
    public static final int FRAGMENT_MINE =  2;
    public static final int FRAGMENT_SETTING =  3;

    //private PullToRefreshAttacher mPullToRefreshAttacher;
    private SlidingMenu mSlidingMenu;
    private SettingFragment mSettingFragment;
    private BaseFragment[] fragments = new BaseFragment[4];
    private TabsFragment mTabsFragment = new TabsFragment(new TabsFragment.TabFactory() {
        @Override
        public BaseFragment newInstant(int index) {
            switch (index){
                case FRAGMENT_GOODS:
                    fragments[index] = new GoodsFragment();
                    break;
                case FRAGMENT_PUBLISH:
                    fragments[index] = new PublishFragment();
                    break;
                case FRAGMENT_MINE:
                    fragments[index] = new MineFragment();
                    break;
                case FRAGMENT_SETTING:
                    fragments[index] = new RankFragment();
                    break;
            }
            return fragments[index];
        }
    });

    private void initSlidingMenu() {
        WindowManager manage=getWindowManager();
        Display display=manage.getDefaultDisplay();
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setFadeEnabled(false);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        mSlidingMenu.setShadowWidth(15);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindScrollScale(0);
        mSlidingMenu.setBehindOffset((int) (display.getWidth()* Constants.MENU_OFFSET_PERCENTAGE));
        mSlidingMenu.setMenu(R.layout.fragment_menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initSlidingMenu();
        //setPullToRefreshAttacher();
    }

    private void initFragment() {
        mSettingFragment = new SettingFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_tab_fragments, mTabsFragment)
                .replace(R.id.fragment_container_menu_left, mSettingFragment)
                .commit();
    }

    /*private void setPullToRefreshAttacher(){
        PullToRefreshAttacher.Options options = new PullToRefreshAttacher.Options();
        options.headerInAnimation = R.anim.pulldown_fade_in;
        options.headerOutAnimation = R.anim.pulldown_fade_out;
        options.refreshScrollDistance = 0.3f;
        options.headerLayout = R.layout.pulldown_header;
        mPullToRefreshAttacher = new PullToRefreshAttacher(MainActivity.this,options);
    }*/

    /*public PullToRefreshAttacher getmPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }*/

    @Override
    public void onBackPressed() {
        if(mSlidingMenu.isMenuShowing()) {
            mSlidingMenu.toggle();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}

package com.yhd.think.leyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.fragment.BaseFragment;
import com.yhd.think.leyi.fragment.GoodsFragment;
import com.yhd.think.leyi.fragment.MineFragment;
import com.yhd.think.leyi.fragment.PublishFragment;
import com.yhd.think.leyi.fragment.SettingFragment;
import com.yhd.think.leyi.fragment.TabsFragment;
import com.yhd.think.leyi.view.TabsView;


public class MainActivity extends Activity {

    public static final int FRAGMENT_GOODS =  0;
    public static final int FRAGMENT_PUBLISH =  1;
    public static final int FRAGMENT_MINE =  2;
    public static final int FRAGMENT_SETTING =  3;

    //private TabsView mTabsView;


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
                    fragments[index] = new SettingFragment();
                    break;
            }
            return fragments[index];
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initFragment();
        //initTabsView();
    }

    /*private void initTabsView() {
        mTabsView = (TabsView) findViewById(R.id.activity_my_tab_view);
    }*/

    private void initFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_tab_fragments, mTabsFragment)
                .commit();
    }

}

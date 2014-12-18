package com.yhd.think.leyi.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.MainActivity;

/**
 *
 * @author snow
 *
 */
public class TabsFragment extends Fragment implements View.OnClickListener{

    private View rootView;

    private RelativeLayout rl_goods;
    private RelativeLayout rl_publish;
    private RelativeLayout rl_mine;
    private RelativeLayout rl_setting;

    //private OnTabClickListener mListener;
    private TabFactory mFactory;
    private BaseFragment[] mFragments = new BaseFragment[4];

    private int defaultItem;

    /*public interface OnTabClickListener{
        public void onClick(int index);
    }*/

    public interface TabFactory{
        public BaseFragment newInstant(int index);
    }

    public TabsFragment() {

    }

    @SuppressLint("ValidFragment")
    public TabsFragment(TabFactory mFactory) {
        super();
        this.mFactory = mFactory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        findViews();
        setViews();
        showFragment(defaultItem);
        return rootView;
    }

    private void setViews() {
        rl_goods.setOnClickListener(this);
        rl_publish.setOnClickListener(this);
        rl_mine.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
    }

    private void findViews() {
        rl_goods = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_goods);
        rl_publish = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_publish);
        rl_mine = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_mine);
        rl_setting = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_setting);
    }

    /*private synchronized void selectTab(int index){
        switch (index){
            case MainActivity.FRAGMENT_GOODS:
                break;
            case MainActivity.FRAGMENT_PUBLISH:
                break;
            case MainActivity.FRAGMENT_MINE:
                break;
            case MainActivity.FRAGMENT_SETTING:
                break;
        }
    }*/

    private synchronized void showFragment(int index){
        if(mFragments[index] == null){
            mFragments[index] = mFactory.newInstant(index);
        }
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.activity_base_fragments, mFragments[index])
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tabs_fragment_goods:
                showFragment(MainActivity.FRAGMENT_GOODS);
                break;
            case R.id.tabs_fragment_publish:
                showFragment(MainActivity.FRAGMENT_PUBLISH);
                break;
            case R.id.tabs_fragment_mine:
                showFragment(MainActivity.FRAGMENT_MINE);
                break;
            case R.id.tabs_fragment_setting:
                showFragment(MainActivity.FRAGMENT_SETTING);
                break;
        }
    }

    /*public OnTabClickListener getmListener() {
        return mListener;
    }

    public void setmListener(OnTabClickListener mListener) {
        this.mListener = mListener;
    }*/

    public TabFactory getmFactory() {
        return mFactory;
    }

    public void setmFactory(TabFactory mFactory) {
        this.mFactory = mFactory;
    }

    public int getDefaultItem() {
        return defaultItem;
    }

    public void setDefaultItem(int defaultItem) {
        this.defaultItem = defaultItem;
    }
}

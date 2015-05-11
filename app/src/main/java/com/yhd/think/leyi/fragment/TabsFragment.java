package com.yhd.think.leyi.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.MainActivity;

/**
 * @author snow
 */
public class TabsFragment extends Fragment implements View.OnClickListener{
    //最下层的五个按钮id
    public static final int FRAGMENT_FIRST =  0;
    public static final int FRAGMENT_SECOND =  1;
    public static final int FRAGMENT_THIRD =  2;
    public static final int FRAGMENT_FOURTH =  3;
    public static final int FRAGMENT_FIVETH =  4;
    public static final int FRAGMENT_COUNT = 5; //按钮的数量

    private View rootView;
    //五个按钮布局？？？
    private RelativeLayout rl_first;
    private RelativeLayout rl_second;
    private RelativeLayout rl_third;
    private RelativeLayout rl_fourth;
    private RelativeLayout rl_fiveth;

    private TabFactory mFactory;
    private TabClickListener mListener;
    private BaseFragment[] mFragments; //中间的fragment

    private int resId; //fragment布局
    private int defaultItem = 0;//默认显示的Fragment
    private int currentItem; //当前显示的Fragment

    public interface TabFactory{
        public BaseFragment newInstant(int index);
    }

    public interface TabClickListener{  //按钮监听器
        public void onClick(int index);
    }

    public TabsFragment() {
    }

    @SuppressLint("ValidFragment")
    public TabsFragment(TabFactory mFactory, int resId) {
        super();
        this.mFactory = mFactory;
        currentItem = FRAGMENT_COUNT;
        this.resId = resId;
        mFragments = new BaseFragment[FRAGMENT_COUNT];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tabs, container, false);//加载五个按钮
        findViews();
        setViews();
        showFragment(defaultItem);
        return rootView;
    }

    private void setViews() {
        rl_first.setOnClickListener(this);
        rl_second.setOnClickListener(this);
        rl_third.setOnClickListener(this);
        rl_fourth.setOnClickListener(this);
        rl_fiveth.setOnClickListener(this);
    }

    private void findViews() {
        rl_first = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_first);
        rl_second = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_second);
        rl_third = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_third);
        rl_fourth = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_fourth);
        rl_fiveth = (RelativeLayout) rootView.findViewById(R.id.tabs_fragment_fiveth);
    }

    private synchronized void selectTab(int index){
        setAllNotClicked();//按钮清零
        switch (index){
            case FRAGMENT_FIRST:
                rl_first.setSelected(true);
                break;
            case FRAGMENT_SECOND:
                rl_second.setSelected(true);
                break;
            case FRAGMENT_THIRD:
                rl_third.setSelected(true);
                break;
            case FRAGMENT_FOURTH:
                rl_fourth.setSelected(true);
                break;
            case FRAGMENT_FIVETH:
                rl_fiveth.setSelected(true);
                break;
        }
    }

    private void setAllNotClicked(){
        rl_first.setSelected(false);
        rl_second.setSelected(false);
        rl_third.setSelected(false);
        rl_fourth.setSelected(false);
        rl_fiveth.setSelected(false);
    }

    public synchronized void showFragment(int index){
        if(index == currentItem){
            return;
        }
        if(mFragments[index] == null){
            mFragments[index] = mFactory.newInstant(index);
        }
        selectTab(index);
        if(!mFragments[index].isAdded()){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(resId, mFragments[index])
                    .commit();
            currentItem = index;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tabs_fragment_first:
                mListener.onClick(FRAGMENT_FIRST);
                break;
            case R.id.tabs_fragment_second:
                mListener.onClick(FRAGMENT_SECOND);
                break;
            case R.id.tabs_fragment_third:
                mListener.onClick(FRAGMENT_THIRD);
                break;
            case R.id.tabs_fragment_fourth:
                mListener.onClick(FRAGMENT_FOURTH);
                break;
            case R.id.tabs_fragment_fiveth:
                mListener.onClick(FRAGMENT_FIVETH);
                break;
        }
    }

    public TabFactory getmFactory() {
        return mFactory;
    }//为什么不是静态的??

    public void setmFactory(TabFactory mFactory) {
        this.mFactory = mFactory;
    }

    public int getDefaultItem() {
        return defaultItem;
    }

    public void setDefaultItem(int defaultItem) {
        this.defaultItem = defaultItem;
    }

    public TabClickListener getmListener() {
        return mListener;
    }

    public void setmListener(TabClickListener mListener) {
        this.mListener = mListener;
    }
}

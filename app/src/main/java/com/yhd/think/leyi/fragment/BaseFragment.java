package com.yhd.think.leyi.fragment;



import android.app.Application;
import android.content.Intent;
import android.app.Fragment;

import com.yhd.think.leyi.R;
import com.yhd.think.leyi.context.MainApplication;

import java.lang.reflect.Field;

/**
 * @author snow
 *
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
    }

    public MainApplication getMyApplication(){
        return (MainApplication)getActivity().getApplication();
    }

}

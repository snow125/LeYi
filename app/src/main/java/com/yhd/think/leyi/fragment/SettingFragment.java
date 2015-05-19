package com.yhd.think.leyi.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.activity.AboutActivity;
import com.yhd.think.leyi.activity.ListActivity;
import com.yhd.think.leyi.activity.LoginActivity;
import com.yhd.think.leyi.activity.MineActivity;
import com.yhd.think.leyi.activity.SuggestionActivity;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.view.CircleImageView;
import com.yhd.think.leyi.view.NetworkCircleImageView;
import com.yhd.think.leyi.view.SwitchView;

/**
 *
 * @author snow
 *
 */
public class SettingFragment extends BaseFragment {

    private static User user = User.getInstance();
    private View rootView;
    private TextView checkUpdate;
    private TextView suggestion;
    private TextView tel;
    private TextView about;
    private SwitchView swith;
    private DialogTool telDialog;
    private DialogTool updateDialog;
    private static NetworkCircleImageView head;
    private static TextView name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        findViews();
        initDialog();
        setListeners();
        return rootView;
    }

    private void initDialog() {
        telDialog = new DialogTool() {
            @Override
            public void positive() {

            }

            @Override
            public void negative() {

            }
        };

        updateDialog = new DialogTool() {
            @Override
            public void positive() {

            }

            @Override
            public void negative() {

            }
        };
    }

    private void setListeners() {
        checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.showDialog(getActivity(),"更新？","是否现在更新","更新吧","再想想");
            }
        });
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SuggestionActivity.class);
                startActivity(i);
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telDialog.showDialog(getActivity(),"致电客服","是否现在致电客服","打吧","再想想");
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AboutActivity.class);
                startActivity(i);
            }
        });
//        swith.setOnSwitchChangeListener(new SwitchView.OnSwitchChangeListener() {
//            @Override
//            public void onSwitchChanged(View view, boolean isOn) {
//                getMyApplication().setHavePic(isOn);
//            }
//        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isLogin()){
                    Intent i = new Intent(getActivity(), MineActivity.class);
                    i.putExtra("ismine",true);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(i, LoginActivity.SUCCESS);
                }

            }
        });
        updateInfo();
    }

    public static void updateInfo(){
        if(user.isLogin()){
            head.setImageUrl(user.getFaceUrl(), ImageCacheManager.getInstance().getImageLoader());
            head.setErrorImageResId(R.drawable.anonymous_icon);
            head.setDefaultImageResId(R.drawable.anonymous_icon);
            name.setText(user.getName());
        }else{
            //head.setBackgroundResource(R.drawable.anonymous_icon);
            head.setImageUrl(null, ImageCacheManager.getInstance().getImageLoader());
            head.setErrorImageResId(R.drawable.anonymous_icon);
            head.setDefaultImageResId(R.drawable.anonymous_icon);
            name.setText("请登录");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == LoginActivity.SUCCESS){
            updateInfo();
            //SettingFragment.updateInfo();
            GoodsFragment.updateInfo();
            ServeceFragment.updateInfo();
            PublishFragment.updateInfo();
            RankFragment.updateInfo();
        }
    }

    private void findViews() {
        checkUpdate = (TextView) rootView.findViewById(R.id.fragment_setting_check_update);
        suggestion = (TextView) rootView.findViewById(R.id.fragment_setting_suggestion);
        tel = (TextView) rootView.findViewById(R.id.fragment_setting_tel);
        about = (TextView) rootView.findViewById(R.id.fragment_setting_about);
        //swith = (SwitchView) rootView.findViewById(R.id.fragment_setting_switch);
        head = (NetworkCircleImageView) rootView.findViewById(R.id.fragment_setting_head);
        name = (TextView) rootView.findViewById(R.id.fragment_setting_name);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SettingFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("SettingFragment");
    }

}

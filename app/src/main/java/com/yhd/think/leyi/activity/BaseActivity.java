package com.yhd.think.leyi.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.android.volley.toolbox.JsonObjectRequest;
import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.network.RequestManager;

import java.util.Objects;

public class BaseActivity extends Activity {

    private Objects obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.getRequestQueue().cancelAll(obj);
    }

    public void addRequestToQueen(JsonObjectRequest jsonObjectRequest){
        jsonObjectRequest.setTag(obj);
        RequestManager.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
    }

    public Application getMyApplication(){
        return getApplication();
    }

}

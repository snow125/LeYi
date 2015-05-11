package com.yhd.think.leyi.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yhd.think.leyi.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class IMActivity extends BaseActivity {

    private String token = "ZhAK8qdE+Z+Kngn45RdHJufl03QnPPU0p4iH1FzPCDjR/QfGEaeEHzgYysrUMsDmWG4hnDyLWWFI4BZcVj5ugw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        try {
            rongCloud();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rongCloud() throws Exception {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("123", "succeed");
            }

            @Override
            public void onError(ErrorCode errorCode) {
                Log.e("123", "fail");
            }
        });
    }

    public void startChart(View view){
        String userId = "55";
        String title = "自问自答";
        RongIM.getInstance().startPrivateChat(this, userId, title);
    }

}

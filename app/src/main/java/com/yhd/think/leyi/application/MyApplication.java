package com.yhd.think.leyi.application;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.network.RequestManager;

/**
 * @author snow
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        RequestManager.init(this);

        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.setDebugMode(true);
    }
}

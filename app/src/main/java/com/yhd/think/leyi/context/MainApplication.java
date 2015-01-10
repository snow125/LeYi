package com.yhd.think.leyi.context;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.RequestManager;
import com.yhd.think.leyi.tools.DialogTool;


/**
 * Created by snow on 2015/1/2.
 */
public class MainApplication extends Application {

    private boolean havePic;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        RequestManager.init(this);

        User.init(this);

        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.setDebugMode(true);
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
        getSharedPreferences(Constants.TAG_SP_KEY_SETTING, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(Constants.TAG_SP_KEY_HAVE_PICTURE,havePic)
                .commit();
    }
}

package com.yhd.think.leyi.context;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.yhd.think.leyi.data.Constants;
import com.yhd.think.leyi.data.User;
import com.yhd.think.leyi.network.RequestManager;
import com.yhd.think.leyi.network.image.ImageCacheManager;
import com.yhd.think.leyi.tools.DialogTool;
import com.yhd.think.leyi.tools.SharedPrefsUtil;

import io.rong.imkit.RongIM;


/**
 * Created by snow on 2015/1/2.
 */
public class MainApplication extends Application {

    private static int DISK_IMAGECACHE_SIZE = 1024*1024*30;
    private static Bitmap.CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided

    private boolean havePic;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        RequestManager.init(this);

        SharedPrefsUtil.init(this);

        User.init(this);

        ImageCacheManager.getInstance().init(this,
                this.getPackageCodePath()
                , DISK_IMAGECACHE_SIZE
                , DISK_IMAGECACHE_COMPRESS_FORMAT
                , DISK_IMAGECACHE_QUALITY
                , ImageCacheManager.CacheType.DISK);

        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.setDebugMode(true);

        RongIM.init(this);
        //SDKInitializer.initialize(this);
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

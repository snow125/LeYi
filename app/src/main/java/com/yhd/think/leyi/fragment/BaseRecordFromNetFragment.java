package com.yhd.think.leyi.fragment;

import android.app.Activity;
import android.media.MediaPlayer;

import com.yhd.think.leyi.fragment.BaseFragment;

import java.io.IOException;

/**
 * Created by Think on 2015/5/19.
 */
public class BaseRecordFromNetFragment extends BaseFragment {

    protected void startMP3(String url){
        MediaPlayer mp = createNetMp3(url);
        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    private MediaPlayer createNetMp3(String url){
        MediaPlayer mp=new MediaPlayer();
        try {
            mp.setDataSource(url);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalStateException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return mp;
    }
}

package com.yhd.think.leyi.tools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Think on 2015/2/22.
 */
public class TelDialog {

    private DialogTool telLog;
    private Activity context;

    public TelDialog(Activity context, final String tel){
        this.context = context;
        telLog = new DialogTool() {
            @Override
            public void positive() {
                toTel(tel);
            }

            @Override
            public void negative() {

            }
        };
    }

    public void show(){
        telLog.showDialog(context, "电话","是否拨打电话","快打","算了");
    }

    private void toTel(String tel){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
        context.startActivity(intent);
    }

}

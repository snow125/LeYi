package com.yhd.think.leyi.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Think on 2015/2/22.
 */
public class MessageDialog {

    private DialogTool messageLog;
    private Activity context;

    public MessageDialog(Activity context, final String tel, final String message){
        this.context = context;
        messageLog = new DialogTool() {
            @Override
            public void positive() {
                toMessage(tel,message);
            }

            @Override
            public void negative() {

            }
        };
    }

    public void show(){
        messageLog.showDialog(context,"短信","是否发送短信","快发","算了");
    }

    private void toMessage(String tel, String content) {
        Uri smsToUri = Uri.parse("smsto:"+tel);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }

}

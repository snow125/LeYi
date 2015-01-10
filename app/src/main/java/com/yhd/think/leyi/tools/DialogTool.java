package com.yhd.think.leyi.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by snow on 2015/1/9.
 */
public abstract class DialogTool {

    public void showDialog(Context context, String title, String message, String positive, String negative){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog mAlertDialog = builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positive();
                    }
                })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        negative();
                    }
                })
                .create();
        mAlertDialog.show();
    }

    public abstract void positive();

    public abstract void negative();

}

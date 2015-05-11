package com.yhd.think.leyi.tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by snow on 2015/1/9.
 */
public class ToastTool {

    public static Toast toast;

    public static void showToast(Context context, String text) {
        if(context != null){
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}

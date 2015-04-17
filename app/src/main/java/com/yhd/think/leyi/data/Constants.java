package com.yhd.think.leyi.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by snow on 2015/1/6.
 */
public class Constants {

    public static final String BASE_URL = "http://123.57.253.239:8080/lyApp/";
    public static final int PAGE_SIZE = 8;
    public static final int JSON_OK = 0;

    public static final float MENU_OFFSET_PERCENTAGE = 0.35f;

    public static final String TAG_SP_KEY_HAVE_PICTURE = "have_picture";
    public static final String TAG_SP_KEY_SETTING = "setting";

    public static final String SP_NAME = "name";
    public static final String SP_WORD = "word";
    public static final String SP_FACEURL = "faceurl";
    public static final String SP_LOGIN = "islogin";
    public static final String SP_PASSWORD = "password";
    public static final String SP_TEL = "tel";
    public static final String SP_ID = "id";
    public static final String TAG_SP = "user";

    public static final int TYPE_CLICK = 0;
    public static final int TYPE_TIME = 1;
    public static final int TYPE_PRETTY = 2;
    public static final int TYPE_NULL = 3;
    public static final int TYPE_DISTANCE = 4;

    public static final Map<String, Integer> type2id = new HashMap<String, Integer>();

    static{
        type2id.put("书籍", 1);
        type2id.put("手机/配件", 2);
        type2id.put("电脑/配件", 3);
        type2id.put("电器", 4);
        type2id.put("服装", 5);
        type2id.put("其他", 6);
        type2id.put("服务", 7);
    }

    public static final String[] collageData = new String[]{
            "西北大学","西安交通大学",
            "西北工业大学","西安理工大学",
            "西安电子科技大学","西安工业大学",
            "西安建筑科技大学","西安科技大学",
            "西安石油大学","陕西科技大学",
            "西安工程大学","长安大学",
            "西北农林科技大学","陕西中医学院",
            "陕西师范大学","延安大学",
            "陕西理工学院","宝鸡文理学院",
            "咸阳师范学院","渭南师范学院",
            "西安外国语大学","西北政法大学",
            "西安体育学院","西安音乐学院",
            "西安美术学院","陕西工业职业技术学院"};



}

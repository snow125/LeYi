package com.yhd.think.leyi.data;

/**
 * Created by snow on 2015/1/5.
 */
public class Image {

    private String url;

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 测试使用
     */
    private int resId;

    public Image(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}

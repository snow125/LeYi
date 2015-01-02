package com.yhd.think.leyi.data;

/**
 * @author snow
 */
public class Goods {

    private String imageUrl;
    private String name;
    private String request;

    /**
     * 供前期测试使用
     */
    private int bitmapResouceId;

    public int getBitmapResouceId() {
        return bitmapResouceId;
    }

    public void setBitmapResouceId(int bitmapResouceId) {
        this.bitmapResouceId = bitmapResouceId;
    }

    public Goods(String name, String request, int bitmapResouceId) {
        this.name = name;
        this.request = request;
        this.bitmapResouceId = bitmapResouceId;
    }

    /**
     * 正式：
     */

    public Goods(String imageUrl, String name, String request) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.request = request;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }


}

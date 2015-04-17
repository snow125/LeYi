package com.yhd.think.leyi.data;

import java.util.Date;

/**
 * @author snow
 */
public class Goods {

    private String id;
    private String imageUrl;//用户上传的第一张图片的URL
    private String name;
    private String request;
    /*
    *下面三个怎么交互？Json貌似只能传String对象
    */
    private Date time;
    private long pretty;//不需要
    private long clickNum;

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

    public Goods(String name, String request, int bitmapResouceId, Date time, long clickNum) {
        this.name = name;
        this.request = request;
        this.bitmapResouceId = bitmapResouceId;
        this.time = time;
        this.clickNum = clickNum;
    }

    public Goods(String name, String request, int bitmapResouceId, Date time) {
        this.name = name;
        this.request = request;
        this.bitmapResouceId = bitmapResouceId;
        this.time = time;
    }

    public Goods(String name, String request, int bitmapResouceId, long pretty) {
        this.name = name;
        this.request = request;
        this.bitmapResouceId = bitmapResouceId;
        this.pretty = pretty;
    }

    public Goods(String name, String request, int bitmapResouceId) {
        this.name = name;
        this.request = request;
        this.bitmapResouceId = bitmapResouceId;
    }

    public Goods(String id, String imageUrl, String name, String request, long clickNum, Date time) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.request = request;
        this.clickNum = clickNum;
        this.time = time;
    }

    /**
     * 正式：
     */



    public Goods(String imageUrl, String name, String request) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.request = request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getClickNum() {
        return clickNum;
    }

    public void setClickNum(long clickNum) {
        this.clickNum = clickNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getPretty() {
        return pretty;
    }

    public void setPretty(long pretty) {
        this.pretty = pretty;
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

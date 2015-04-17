package com.yhd.think.leyi.data;

import java.util.Date;

/**
 * @author snow
 */
public class GoodsDetail {

    private Date time;
    private String id;

    private String ownerImageUrl;
    private String ownerName;
    private String ownerSign;
    private String ownerTel;
    private String money;
    private String service;
    private String good;
    private String description;
    private String name;
    private String type;//用什么方式传递？
    private int imageNum;
    private String[] imageUrls;

    //private GoodsStatus status;
    //private String time;
    //private long clickNum;
    //private String imageUrl;
    private int ownerImageId;
    private int imageId;
    private int[] imageIds;

    public GoodsDetail(String ownerImageUrl, String ownerName, String ownerSign, String ownerTel, String money, String service, String good, String description, String name, int imageNum, String[] imageUrls) {
        this.ownerImageUrl = ownerImageUrl;
        this.ownerName = ownerName;
        this.ownerSign = ownerSign;
        this.ownerTel = ownerTel;
        this.money = money;
        this.service = service;
        this.good = good;
        this.description = description;
        this.name = name;
        this.imageNum = imageNum;
        this.imageUrls = imageUrls;
    }

    public GoodsDetail(int[] imageIds, int ownerImageId, int imageNum, String name, String description, String request, String ownerTel, String ownerSign, String ownerName) {
        this.imageIds = imageIds;
        this.ownerImageId = ownerImageId;
        this.imageNum = imageNum;
        //this.type = type;
        this.name = name;
        this.description = description;
        //this.request = request;
        //this.status = status;
        //this.clickNum = clickNum;
        //this.time = time;
        this.ownerTel = ownerTel;
        this.ownerSign = ownerSign;
        this.ownerName = ownerName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int[] getImageIds() {
        return imageIds;
    }

    public void setImageIds(int[] imageIds) {
        this.imageIds = imageIds;
    }

    public String getOwnerImageUrl() {
        return ownerImageUrl;
    }

    public void setOwnerImageUrl(String ownerImageUrl) {
        this.ownerImageUrl = ownerImageUrl;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSign() {
        return ownerSign;
    }

    public void setOwnerSign(String ownerSign) {
        this.ownerSign = ownerSign;
    }

    public String getOwnerTel() {
        return ownerTel;
    }

    public void setOwnerTel(String ownerTel) {
        this.ownerTel = ownerTel;
    }

    /*public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/

    /*public long getClickNum() {
        return clickNum;
    }

    public void setClickNum(long clickNum) {
        this.clickNum = clickNum;
    }*/

    /*public GoodsStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsStatus status) {
        this.status = status;
    }*/



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwnerImageId() {
        return ownerImageId;
    }

    public void setOwnerImageId(int ownerImageId) {
        this.ownerImageId = ownerImageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

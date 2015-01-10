package com.yhd.think.leyi.data;

/**
 * @author snow
 */
public class GoodsDetail {

    private String ownerImageUrl;
    private String ownerName;
    private String ownerSign;
    private String ownerTel;
    private String time;
    private long clickNum;
    private GoodsStatus status;
    private String hopeMemo;
    private String description;
    private String imageUrl;
    private String name;
    private String type;

    private int ownerImageId;
    private int imageId;


    public GoodsDetail(String ownerName, String ownerSign, String ownerTel, String hopeMemo, String description, String name, int imageId, int ownerImageId) {
        this.ownerName = ownerName;
        this.ownerSign = ownerSign;
        this.ownerTel = ownerTel;
        this.hopeMemo = hopeMemo;
        this.description = description;
        this.name = name;
        this.imageId = imageId;
        this.ownerImageId = ownerImageId;
    }

    public GoodsDetail(String ownerImageUrl, String ownerName, String ownerSign, String ownerTel, String time, long clickNum, GoodsStatus status, String hopeMemo, String description, String imageUrl, String name, String type) {
        this.ownerImageUrl = ownerImageUrl;
        this.ownerName = ownerName;
        this.ownerSign = ownerSign;
        this.ownerTel = ownerTel;
        this.time = time;
        this.clickNum = clickNum;
        this.status = status;
        this.hopeMemo = hopeMemo;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getClickNum() {
        return clickNum;
    }

    public void setClickNum(long clickNum) {
        this.clickNum = clickNum;
    }

    public GoodsStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsStatus status) {
        this.status = status;
    }

    public String getHopeMemo() {
        return hopeMemo;
    }

    public void setHopeMemo(String hopeMemo) {
        this.hopeMemo = hopeMemo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

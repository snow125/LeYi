package com.yhd.think.leyi.data;

/**
 * Created by snow on 2015/1/26.
 */
public class ExperienceDetail {

    private String sellImageUrl;
    private String shopImageUrl;
    private String sellGoodUrl;
    private String shopGoodUrl;
    private String sellWord;
    private String shopWord;

    private int sellImageID;
    private int shopImageID;
    private int sellGoodID;
    private int shopGoodID;

    public ExperienceDetail(int shopGoodID, int sellGoodID, int shopImageID, int sellImageID, String shopWord, String sellWord) {
        this.shopGoodID = shopGoodID;
        this.sellGoodID = sellGoodID;
        this.shopImageID = shopImageID;
        this.sellImageID = sellImageID;
        this.shopWord = shopWord;
        this.sellWord = sellWord;
    }

    public String getSellImageUrl() {
        return sellImageUrl;
    }

    public void setSellImageUrl(String sellImageUrl) {
        this.sellImageUrl = sellImageUrl;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public void setShopImageUrl(String shopImageUrl) {
        this.shopImageUrl = shopImageUrl;
    }

    public String getShopGoodUrl() {
        return shopGoodUrl;
    }

    public void setShopGoodUrl(String shopGoodUrl) {
        this.shopGoodUrl = shopGoodUrl;
    }

    public String getSellGoodUrl() {
        return sellGoodUrl;
    }

    public void setSellGoodUrl(String sellGoodUrl) {
        this.sellGoodUrl = sellGoodUrl;
    }

    public String getSellWord() {
        return sellWord;
    }

    public void setSellWord(String sellWord) {
        this.sellWord = sellWord;
    }

    public String getShopWord() {
        return shopWord;
    }

    public void setShopWord(String shopWord) {
        this.shopWord = shopWord;
    }

    public int getSellImageID() {
        return sellImageID;
    }

    public void setSellImageID(int sellImageID) {
        this.sellImageID = sellImageID;
    }

    public int getSellGoodID() {
        return sellGoodID;
    }

    public void setSellGoodID(int sellGoodID) {
        this.sellGoodID = sellGoodID;
    }

    public int getShopImageID() {
        return shopImageID;
    }

    public void setShopImageID(int shopImageID) {
        this.shopImageID = shopImageID;
    }

    public int getShopGoodID() {
        return shopGoodID;
    }

    public void setShopGoodID(int shopGoodID) {
        this.shopGoodID = shopGoodID;
    }
}

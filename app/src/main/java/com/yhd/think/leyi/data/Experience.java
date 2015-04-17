package com.yhd.think.leyi.data;

import java.util.Date;

/**
 * Created by snow on 2015/1/26.
 */
public class Experience {

    private String sellGoodUrl;
    private String shopGoodUrl;
    private Date time;
    private long pretty;

    private int sellGoodID;
    private int shopGoodID;

    public Experience(int shopGoodID, int sellGoodID, long pretty, Date time) {
        this.shopGoodID = shopGoodID;
        this.sellGoodID = sellGoodID;
        this.pretty = pretty;
        this.time = time;
    }

    public Experience(String sellGoodUrl, String shopGoodUrl, Date time, long pretty) {
        this.sellGoodUrl = sellGoodUrl;
        this.shopGoodUrl = shopGoodUrl;
        this.time = time;
        this.pretty = pretty;
    }

    public String getSellGoodUrl() {
        return sellGoodUrl;
    }

    public void setSellGoodUrl(String sellGoodUrl) {
        this.sellGoodUrl = sellGoodUrl;
    }

    public String getShopGoodUrl() {
        return shopGoodUrl;
    }

    public void setShopGoodUrl(String shopGoodUrl) {
        this.shopGoodUrl = shopGoodUrl;
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

    public int getSellGoodID() {
        return sellGoodID;
    }

    public void setSellGoodID(int sellGoodID) {
        this.sellGoodID = sellGoodID;
    }

    public int getShopGoodID() {
        return shopGoodID;
    }

    public void setShopGoodID(int shopGoodID) {
        this.shopGoodID = shopGoodID;
    }
}

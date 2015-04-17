package com.yhd.think.leyi.data;

/**
 * Created by snow on 2015/1/6.
 */
public class Comment {

    private String ownerName;
    private String ownerConmment;
    private String ownerUrl;

    private int ownerPic;

    public Comment(String ownerName, String ownerConmment, int ownerPic) {
        this.ownerName = ownerName;
        this.ownerConmment = ownerConmment;
        this.ownerPic = ownerPic;
    }

    public Comment(String ownerName, String ownerConmment, String ownerUrl) {
        this.ownerName = ownerName;
        this.ownerConmment = ownerConmment;
        this.ownerUrl = ownerUrl;
    }

    public String getOwnerConmment() {
        return ownerConmment;
    }

    public void setOwnerConmment(String ownerConmment) {
        this.ownerConmment = ownerConmment;
    }

    public int getOwnerPic() {
        return ownerPic;
    }

    public void setOwnerPic(int ownerPic) {
        this.ownerPic = ownerPic;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}

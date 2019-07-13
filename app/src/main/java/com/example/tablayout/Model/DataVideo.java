package com.example.tablayout.Model;

import java.io.Serializable;

public class DataVideo implements Serializable {
    private String tieuDe;
    private long size;
    private String urlVideo;
    private String date;
    private String linkAvatar;

    public DataVideo() {
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getDate() {
        return date.substring(0,10);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }
}

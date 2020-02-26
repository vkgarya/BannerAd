package com.coupon.ad.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class AdAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private int id;
    @JsonIgnore
    private String aspect_ratio;
    private String resolution;
    private String link;


    public void setId(int id) {
        this.id = id;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AdAsset{" +
                "id=" + id +
                ", aspect_ratio='" + aspect_ratio + '\'' +
                ", resolution='" + resolution + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

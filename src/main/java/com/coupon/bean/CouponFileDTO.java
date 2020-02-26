package com.coupon.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponFileDTO {
    @JsonProperty("file_type")
    private String fileType;

    private String url;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

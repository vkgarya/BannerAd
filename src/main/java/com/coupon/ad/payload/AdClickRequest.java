package com.coupon.ad.payload;

public class AdClickRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdClickRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}

package com.coupon.auth.payload;

import com.coupon.bean.StatusDTO;

public class AuthResponse {
    private String access_token;
    public AuthResponse() {

    }

    public AuthResponse(final String accessToken) {
        this.access_token = accessToken;
    }

    public AuthResponse(String access_token, StatusDTO statusDTO,String txn_id) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}

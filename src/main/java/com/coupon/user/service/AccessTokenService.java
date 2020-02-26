package com.coupon.user.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import com.coupon.user.bean.AccessToken;

public interface AccessTokenService {
    AccessToken save(AccessToken accessToken);

    AccessToken findByToken(String token);

    AccessToken findWithUserByToken(String token);

    void setAuthenticationByUserDetails(HttpServletRequest request, UserDetails userDetails);
}

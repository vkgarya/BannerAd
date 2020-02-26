package com.coupon.auth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.coupon.auth.security.UserPrincipal;
import com.coupon.properties.PropertyManager;
import com.coupon.user.bean.AccessToken;
import com.coupon.user.service.AccessTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenProvider {
    @Resource
    private AccessTokenService accessTokenService;

    public String createToken(final Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String token = generateToken(userPrincipal);

        AccessToken accessToken = new AccessToken();

        accessToken.setUserId(userPrincipal.getId());
        accessToken.setToken(token);

        return accessTokenService.save(accessToken).getToken();
    }

    public String generateToken(final UserPrincipal userPrincipal) {
        Date expiryDate = null;

        try {
            expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2050");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String token = Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setIssuedAt(new Date())
            .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, PropertyManager.getTokenSecret()).compact();

        return token;
    }

    public UserDetails getUserDetailsFromToken(final String token) {
        AccessToken accesstoken = this.accessTokenService.findWithUserByToken(token);

        return UserPrincipal.create(accesstoken.getUser());
    }
}

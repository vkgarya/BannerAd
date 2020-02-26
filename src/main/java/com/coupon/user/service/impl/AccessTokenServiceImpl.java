package com.coupon.user.service.impl;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.AccessToken;
import com.coupon.user.bean.User;
import com.coupon.user.bean.jpa.AccessTokenEntity;
import com.coupon.user.bean.jpa.UserEntity;
import com.coupon.user.repository.AccessTokenRepository;
import com.coupon.user.service.AccessTokenService;
import com.coupon.user.service.UserService;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    @Resource
    private AccessTokenRepository accessTokenRepository;
    @Resource
    private ServiceMapper<AccessToken, AccessTokenEntity> accessTokenServiceMapper;
    @Resource
    private UserService userService;

    @Override
    public AccessToken save(AccessToken accessToken) {
        Optional<AccessTokenEntity> optionalAccessToken = accessTokenRepository.findByToken(accessToken.getToken());
        AccessTokenEntity accessTokenEntity;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(accessToken.getUserId());

        if (optionalAccessToken.isPresent()) {
            accessTokenEntity = optionalAccessToken.get();
        } else {
            accessTokenEntity = new AccessTokenEntity();
            accessTokenEntity.setToken(accessToken.getToken());
            accessTokenEntity.setUserEntity(userEntity);
            accessTokenEntity = accessTokenRepository.save(accessTokenEntity);
        }

        accessToken.setId(accessTokenEntity.getId());

        return accessToken;
    }

    @Override
    public AccessToken findByToken(String token) {
        Optional<AccessTokenEntity> optionalAccessToken = accessTokenRepository.findByToken(token);

        if (!optionalAccessToken.isPresent()) {
            throw new ResourceNotFoundException("Access Token", "token", token);
        }

        AccessToken accessToken = accessTokenServiceMapper.mapEntityToDTO(optionalAccessToken.get(), AccessToken.class);
        accessToken.setUserId(optionalAccessToken.get().getUserEntity().getId());

        return accessToken;
    }

    @Override
    public AccessToken findWithUserByToken(String token) {
        AccessToken accessToken = this.findByToken(token);

        User user = this.userService.findUserWithRolesById(accessToken.getUserId());

        accessToken.setUser(user);

        return accessToken;
    }

    @Override
    public void setAuthenticationByUserDetails(final HttpServletRequest request, final UserDetails userDetails) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

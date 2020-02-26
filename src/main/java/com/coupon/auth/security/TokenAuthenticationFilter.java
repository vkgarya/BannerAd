package com.coupon.auth.security;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coupon.auth.TokenProvider;
import com.coupon.user.service.AccessTokenService;
import com.coupon.utils.CookieUtils;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    @Resource
    private TokenProvider tokenProvider;
    @Resource
    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private AccessTokenService accessTokenService;

    // Checking of the valid token using Client Secret token has been disabled
    // to reduce time taken to process the request
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                UserDetails userDetails = tokenProvider.getUserDetailsFromToken(jwt);
                accessTokenService.setAuthenticationByUserDetails(request, userDetails);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(final HttpServletRequest request) {
        Optional<Cookie> bearerToken = CookieUtils.getCookie(request, "at");
        return bearerToken.map(Cookie::getValue).orElse(null);
    }

    private boolean isAllowedPath(final String path) {
        AntPathMatcher matcher = new AntPathMatcher();
        String[] allowedPaths = {"/",
            "/error",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.json",
            "/**/*.js",
            "/**/design/muiltiple-login",
            "/**/auth/choose-login",
            "/**/auth/set-cookie"
        };

        for (int i = 0; i < allowedPaths.length; i++) {
            if (matcher.match(allowedPaths[i], path)) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();
        String[] allowedPaths = {"/",
            "/error",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
        };

        for (int i = 0; i < allowedPaths.length; i++) {
            if (matcher.match(allowedPaths[i], path)) {
                return true;
            }
        }
        return false;
    }
}

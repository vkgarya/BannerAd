package com.coupon.auth.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.coupon.exceptions.InvalidUserException;

public final class CurrentUserProvider {
    private CurrentUserProvider() {

    }

    public static UserPrincipal getUser() {
        try {
            return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new InvalidUserException();
        }

    }

    public static Integer getUserId() {
        return CurrentUserProvider.getUser().getId();
    }
}

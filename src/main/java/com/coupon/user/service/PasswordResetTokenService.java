package com.coupon.user.service;

import com.coupon.auth.bean.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken create(String email);

    PasswordResetToken findByTokenAndEmail(String token, String email);

    boolean existsByTokenAndEmail(String token, String email);

    PasswordResetToken findByToken(String token);

    void deleteToken(String token);
}

package com.coupon.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.auth.bean.jpa.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends PagingAndSortingRepository<PasswordResetTokenEntity, Integer> {
    Optional<PasswordResetTokenEntity> findByToken(String token);

    Optional<PasswordResetTokenEntity> findByTokenAndEmail(String token, String email);

    @Modifying
    void deleteByToken(String token);
}

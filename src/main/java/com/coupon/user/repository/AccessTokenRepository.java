package com.coupon.user.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.user.bean.jpa.AccessTokenEntity;

public interface AccessTokenRepository extends PagingAndSortingRepository<AccessTokenEntity, Integer> {
    Optional<AccessTokenEntity> findByToken(String token);
}

package com.coupon.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.EndUserEntity;

public interface EndUserRepository extends PagingAndSortingRepository<EndUserEntity, Integer> {
    Optional<EndUserEntity> findByUserId(String userId);

    Optional<EndUserEntity> findByUserReferrerCode(String referrerCode);
}

package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.CouponCodeLanguageMappingEntity;

public interface CouponCodeLanguageMappingRepository extends PagingAndSortingRepository<CouponCodeLanguageMappingEntity, Integer> {
    List<CouponCodeLanguageMappingEntity> findByCouponEntity_idIn(List<Integer> couponIds);

    List<CouponCodeLanguageMappingEntity> findByCouponEntity_id(Integer id);
}

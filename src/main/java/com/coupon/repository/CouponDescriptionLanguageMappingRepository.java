package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.CouponDescriptionLanguageMappingEntity;

public interface CouponDescriptionLanguageMappingRepository extends PagingAndSortingRepository<CouponDescriptionLanguageMappingEntity, Integer> {
    List<CouponDescriptionLanguageMappingEntity> findByCouponEntity_idIn(List<Integer> couponIds);

    List<CouponDescriptionLanguageMappingEntity> findByCouponEntity_id(Integer id);
}

package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.CouponUserRestrictionsEntity;

public interface CouponUserRestrictionsRepository extends PagingAndSortingRepository<CouponUserRestrictionsEntity, Integer> {
    List<CouponUserRestrictionsEntity> findByCouponEntity_id(Integer id);
    List<CouponUserRestrictionsEntity> findByCouponEntity_idIn(List<Integer> ids);
}

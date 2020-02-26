package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.CouponProductRestrictionsEntity;

public interface CouponProductRestrictionsrepository extends PagingAndSortingRepository<CouponProductRestrictionsEntity, Integer> {
    List<CouponProductRestrictionsEntity> findByCouponEntity_id(Integer id);
}

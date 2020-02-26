package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.CouponFileMapEntity;

public interface CouponFileMapRepository extends PagingAndSortingRepository<CouponFileMapEntity, Integer> {
    List<CouponFileMapEntity> findByCouponEntity_id(Integer id);
}

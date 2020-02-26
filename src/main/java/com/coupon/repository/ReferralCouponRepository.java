package com.coupon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.ReferralCouponEntity;

public interface ReferralCouponRepository extends PagingAndSortingRepository<ReferralCouponEntity, Integer> {

}

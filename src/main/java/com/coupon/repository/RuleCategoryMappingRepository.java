package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleCategoryMappingEntity;

public interface RuleCategoryMappingRepository extends PagingAndSortingRepository<RuleCategoryMappingEntity, Integer> {
    Iterable<RuleCategoryMappingEntity> findByCouponEntityIdIn(List<Integer> couponIds);

    List<RuleCategoryMappingEntity> findByCouponEntity_id(Integer id);
}

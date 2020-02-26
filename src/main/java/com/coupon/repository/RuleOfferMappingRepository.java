package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleOfferMappingEntity;

public interface RuleOfferMappingRepository extends PagingAndSortingRepository<RuleOfferMappingEntity, Integer> {
    Iterable<RuleOfferMappingEntity> findByCouponEntityIdIn(List<Integer> couponIds);

    List<RuleOfferMappingEntity> findByCouponEntity_id(Integer id);
}

package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleCalendarMappingEntity;

public interface RuleCalendarMappingRepository extends PagingAndSortingRepository<RuleCalendarMappingEntity, Integer> {
    Iterable<RuleCalendarMappingEntity> findByCouponEntityIdIn(List<Integer> couponIds);

    List<RuleCalendarMappingEntity> findByCouponEntity_id(Integer id);
}

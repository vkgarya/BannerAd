package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleEventEntity;

public interface RuleEventRepository extends PagingAndSortingRepository<RuleEventEntity, Integer> {
    List<RuleEventEntity> findByCouponEntity_id(Integer id);

    List<RuleEventEntity> findByCouponEntity_idIn(List<Integer> couponIds);
}

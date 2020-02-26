package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleTransactionMappingEntity;

public interface RuleTransactionMappingRepository extends PagingAndSortingRepository<RuleTransactionMappingEntity, Integer> {
    List<RuleTransactionMappingEntity> findByCouponEntity_id(Integer id);

    List<RuleTransactionMappingEntity> findByCouponEntity_idIn(List<Integer> couponIds);
}

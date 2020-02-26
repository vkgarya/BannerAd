package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleTransactionFieldsMappingEntity;

public interface RuleTransactionFieldsMappingRepository extends PagingAndSortingRepository<RuleTransactionFieldsMappingEntity, Integer> {
    List<RuleTransactionFieldsMappingEntity> findByRuleTransactionMappingEntity_id(Integer id);

    List<RuleTransactionFieldsMappingEntity> findByRuleTransactionMappingEntity_idIn(List<Integer> ruleTransactionIds);
}

package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.RuleEventFieldEntity;

public interface RuleEventFieldRepository extends PagingAndSortingRepository<RuleEventFieldEntity, Integer> {
    List<RuleEventFieldEntity> findByRuleEventEntity_id(Integer id);

    List<RuleEventFieldEntity> findByRuleEventEntity_idIn(List<Integer> ruleEventIds);
}

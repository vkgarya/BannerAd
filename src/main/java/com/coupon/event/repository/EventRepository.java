package com.coupon.event.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.event.bean.jpa.EventEntity;

public interface EventRepository extends PagingAndSortingRepository<EventEntity, Integer> {
    List<EventEntity> findByTxnId(String txnId);

    List<EventEntity> findByUserId(String userId);
}

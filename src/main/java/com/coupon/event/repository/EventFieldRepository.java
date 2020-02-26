package com.coupon.event.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.event.bean.jpa.EventFieldsEntity;

public interface EventFieldRepository  extends PagingAndSortingRepository<EventFieldsEntity, Integer> {
    List<EventFieldsEntity> findByEventEntity_idIn(List<Integer> userEventIds);
}

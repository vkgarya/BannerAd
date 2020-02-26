package com.coupon.event.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.event.bean.jpa.CustomEventFieldEntity;
import com.coupon.event.constants.EventStatus;

public interface CustomEventFieldRepository extends PagingAndSortingRepository<CustomEventFieldEntity, Integer> {
    Iterable<CustomEventFieldEntity> findByCustomEventsEntity_idAndActiveTrue(Integer id);

    List<CustomEventFieldEntity> findByCustomEventsEntity_idInAndActiveTrue(List<Integer> ids);
}

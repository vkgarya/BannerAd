package com.coupon.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.event.bean.jpa.CustomEventsEntity;
import com.coupon.event.constants.EventStatus;

public interface CustomEventRepository extends PagingAndSortingRepository<CustomEventsEntity, Integer> {
    List<CustomEventsEntity> findByActiveTrue();

    List<CustomEventsEntity> findAll();

    Optional<CustomEventsEntity> findByEventCodeAndActiveTrue(String event_code);
}

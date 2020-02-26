package com.coupon.tracker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.tracker.bean.jpa.TrackerEntity;


public interface TrackerRepository extends PagingAndSortingRepository<TrackerEntity, Integer> {
}

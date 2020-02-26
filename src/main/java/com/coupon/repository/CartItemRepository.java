package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.CartItemEntity;

public interface CartItemRepository extends PagingAndSortingRepository<CartItemEntity, Integer> {
    List<CartItemEntity> findByCartDataEntity_id(Integer userId);
}

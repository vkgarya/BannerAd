package com.coupon.user.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.user.bean.jpa.RoleEntity;

public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Integer> {
    List<RoleEntity> findByIdIn(List<Integer> ids);
}

package com.coupon.user.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.user.bean.jpa.UserRoleEntity;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRoleEntity, Integer> {
    List<UserRoleEntity> findByUserId(Integer id);
}

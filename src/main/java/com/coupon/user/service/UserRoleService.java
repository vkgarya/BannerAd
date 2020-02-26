package com.coupon.user.service;

import java.util.List;

import com.coupon.user.bean.Role;

public interface UserRoleService {
    List<Role> findAllByUserId(Integer id);
}

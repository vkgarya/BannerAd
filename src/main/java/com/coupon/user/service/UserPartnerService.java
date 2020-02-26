package com.coupon.user.service;

import com.coupon.user.bean.Partner;
import com.coupon.user.bean.Role;

import java.util.List;

public interface UserPartnerService {
    List<Partner> findAllByUserId(Integer id);
}

package com.coupon.partner.service;

import com.coupon.user.bean.Partner;
import com.coupon.user.bean.UserPartnerDTO;

import java.util.List;

public interface PartnerService {
    Partner save(Partner Partner);
    Partner findByName(String name);
    List<Partner> findByStatus(String status);

    List<Partner> findAll();

    Partner update(Partner requestBody);

    Partner delete(Integer id);

    void saveUserPartnerMapping(UserPartnerDTO requestBody);

    Partner findById(Integer id);
}

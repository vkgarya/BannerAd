package com.coupon.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.coupon.bean.CouponDTO;
import com.coupon.bean.CouponDataDTO;
import com.coupon.bean.CouponStatus;
import com.coupon.bean.jpa.ConversionDataEntity;
import com.coupon.bean.jpa.CouponEntity;
import com.coupon.bean.jpa.RuleCalendarMappingEntity;

public interface CouponService {
    CouponDTO create(MultipartFile multipartFile, MultipartFile included, MultipartFile offerFile, MultipartFile productsIncluded, MultipartFile file, CouponDataDTO data) throws IOException;

    List<CouponDTO> getActiveCoupons();

    CouponDataDTO getCouponDetails(Integer id);

    CouponDTO edit(MultipartFile offerFile, MultipartFile productsIncluded, MultipartFile file, MultipartFile usersIncluded, MultipartFile usersExcluded, CouponDataDTO data) throws IOException;

    void delete(Integer id);

    Boolean validateCalendarRule(List<RuleCalendarMappingEntity> ruleCalendarMappingEntities);

    List<Integer> filterInvalidCalendarCoupons(Map<Integer, CouponEntity> couponIdCouponMap);

    List<CouponDTO> findCouponsByUserId(Integer id);

    void updateStatus(CouponStatus data);

    List<Integer> getInvalidUserbasedCoupons(Iterable<CouponEntity> coupons, String user);

    List<Integer> getInvalidReferrerCoupons(Iterable<CouponEntity> coupons, String userId);

    Collection<ConversionDataEntity> getSuccessfulConversionsByUserId(String userId);

    List<Integer> getInvalidPartnerCoupons(Iterable<CouponEntity> coupons, String userId);

    List<CouponDTO> getValidCoupons(String userId, String category);

    List<Integer> getInvalidTransactionBasedCoupons(Map<Integer, CouponEntity> couponIdCouponMap, String userId);

    List<Integer> getInvalidEventBasedCoupons(Map<Integer, CouponEntity> couponIdCouponMap, String userId);
}

package com.coupon.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.ConversionDataCouponMappingEntity;

public interface ConversionDataCouponMappingRepository extends PagingAndSortingRepository<ConversionDataCouponMappingEntity, Integer> {
    List<ConversionDataCouponMappingEntity> findByTxnId(String txnId);
    List<ConversionDataCouponMappingEntity> findByCouponEntity_IdIn(List<Integer> ids);
    List<ConversionDataCouponMappingEntity> findByConversionDataEntity_idIn(List<Integer> ids);
}

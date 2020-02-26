package com.coupon.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.coupon.bean.jpa.ReferralBonusMappingEntity;
import com.coupon.constants.ReferralUserType;

public interface ReferralBonusMappingRepository extends PagingAndSortingRepository<ReferralBonusMappingEntity, Integer> {
    @Query("SELECT e FROM ReferralBonusMappingEntity e WHERE e.referralCouponEntity.startedOn < :currentDate AND e.referralCouponEntity.active is true AND (e.referralCouponEntity.closedOn IS NULL OR e.referralCouponEntity.closedOn > :currentDate) " +
            "AND (e.minCartValue is NULL or e.minCartValue < :cartValue) AND e.userType = :userType")
    List<ReferralBonusMappingEntity> findAllValidByUserType(@Param("currentDate") ZonedDateTime date, @Param("cartValue") Double cartValue, @Param("userType")ReferralUserType userType);
}

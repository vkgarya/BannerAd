package com.coupon.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.coupon.bean.jpa.CouponEntity;

public interface CouponRepository extends PagingAndSortingRepository<CouponEntity, Integer> {
    @Query("SELECT e FROM CouponEntity e WHERE e.startedOn < :currentDate AND e.status = :status AND (e.closedOn IS NULL OR e.closedOn > :currentDate) " +
            "AND (e.minCartValue is NULL or (e.minCartValue * :margin) < :cartValue)")
    Iterable<CouponEntity> findAllValid(@Param("currentDate") ZonedDateTime date, @Param("cartValue") Double cartValue, @Param("margin") Double margin, @Param("status") String status);

    @Query("SELECT e FROM CouponEntity e WHERE e.status in :status AND (e.closedOn IS NULL OR e.closedOn > :currentDate)")
    List<CouponEntity> findAllValid(@Param("currentDate") ZonedDateTime date, @Param("status") List<String> status);

    @Query("SELECT e FROM CouponEntity e WHERE e.partnerId in :partnerIds AND e.status in :status AND (e.closedOn IS NULL OR e.closedOn > :currentDate)")
    List<CouponEntity> findAllValidCouponsByUserPartnerIds(@Param("currentDate") ZonedDateTime date, @Param("status") List<String> status,  @Param("partnerIds") List<Integer> partnerIds);
}

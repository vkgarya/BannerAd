package com.coupon.ad.repository;

import com.coupon.ad.bean.jpa.AdEntity;
import com.coupon.ad.constants.AdStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface AdRepository extends PagingAndSortingRepository<AdEntity, Integer> {
    @Query("SELECT a FROM AdEntity a WHERE a.started_on < :now AND (a.closed_on IS NULL OR a.closed_on > :now) " +
            "AND a.type=:type AND a.status = :status ")
    List<AdEntity> findAllValid(@Param("now") ZonedDateTime date, @Param("type") String type, @Param("status") String status);

    @Query("SELECT a FROM AdEntity a WHERE a.type=:type AND a.status != '"+AdStatus.DELETED+"' ")
    List<AdEntity> findAllNotDeleted(@Param("type") String type, Sort sort);

    @Query("SELECT a FROM AdEntity a WHERE a.type=:type AND a.status != '"+AdStatus.DELETED+"' AND a.partner_id in :partnerIds ")
    List<AdEntity> findAllNotDeletedByUserPartnerIds(@Param("type") String type, @Param("partnerIds") List<Integer> partnerIds,Sort sort);

    Optional<AdEntity> findByIdAndType(Integer id, String type);
}

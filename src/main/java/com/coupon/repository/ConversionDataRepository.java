package com.coupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.coupon.bean.jpa.ConversionDataEntity;
import com.coupon.constants.Status;

public interface ConversionDataRepository extends PagingAndSortingRepository<ConversionDataEntity, Integer> {
    List<ConversionDataEntity> findByUserId(String userId);

    Optional<ConversionDataEntity> findFirstByUserIdOrderByIdDesc(String userId);

    @Query("SELECT e FROM ConversionDataEntity e WHERE e.cartDataEntity.txnId = :txnId ORDER BY e.id DESC")
    List<ConversionDataEntity> findByTxnId(@Param("txnId")String txn_id);

    List<ConversionDataEntity> findByUserIdAndStatus(String userId, Status success);
}

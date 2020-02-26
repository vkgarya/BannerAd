package com.coupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.coupon.bean.jpa.CartDataEntity;

public interface CartDataRepository extends PagingAndSortingRepository<CartDataEntity, Integer> {
    @Query("SELECT MAX(e.id) FROM CartDataEntity e WHERE e.txnId = :txnId")
    Integer findLatestIdByTxnId(@Param("txnId")String txnId);

    Optional<CartDataEntity> findFirstByTxnIdOrderByIdDesc(String txnId);

    List<CartDataEntity> findByIdIn(List<Integer> conversionIds);
}

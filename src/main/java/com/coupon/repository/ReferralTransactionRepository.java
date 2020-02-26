package com.coupon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.coupon.bean.jpa.ReferralTransactionEntity;

public interface ReferralTransactionRepository extends PagingAndSortingRepository<ReferralTransactionEntity, Integer> {
    List<ReferralTransactionEntity> findByUserId(String userId);

    Optional<ReferralTransactionEntity> findFirstByTxnIdOrderByIdDesc(String txn_id);
}

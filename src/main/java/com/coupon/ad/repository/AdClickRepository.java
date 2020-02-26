package com.coupon.ad.repository;

import com.coupon.ad.bean.jpa.AdClickEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AdClickRepository extends PagingAndSortingRepository<AdClickEntity, Integer> {
    List<AdClickEntity> findAll();
    AdClickEntity findByAdTxnId(String adTxnId);
}

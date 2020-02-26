package com.coupon.ad.repository;
import com.coupon.ad.bean.jpa.AdRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AdRequestRepository extends PagingAndSortingRepository<AdRequestEntity, Integer> {
    List<AdRequestEntity> findAll();
}

package com.coupon.ad.repository;

import com.coupon.ad.bean.jpa.AdEntity;
import com.coupon.ad.bean.jpa.PartnerAdEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PartnerAdRepository extends PagingAndSortingRepository<PartnerAdEntity, Integer> {
    List<AdEntity> findByPartnerId(Integer id);
}

package com.coupon.ad.repository;

import com.coupon.ad.bean.jpa.AdAssetEntity;
import com.coupon.ad.bean.jpa.AdEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;

public interface AdAssetRepository extends PagingAndSortingRepository<AdAssetEntity, Integer> {
    List<AdAssetEntity> findByAdEntity(AdEntity adEntity);
}

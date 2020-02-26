package com.coupon.ad.service;

import com.coupon.ad.bean.AdAsset;

import java.util.List;

public interface AdAssetService {
    //List<AdAsset> findAllByAdId(Integer id);
    void saveAll(List<AdAsset> adAssetList);
}

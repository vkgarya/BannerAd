package com.coupon.ad.service.impl;

import com.coupon.ad.bean.AdAsset;
import com.coupon.ad.bean.jpa.AdAssetEntity;
import com.coupon.ad.repository.AdAssetRepository;
import com.coupon.ad.service.AssetService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class AssetServiceImpl implements AssetService {
    @Resource
    private AdAssetRepository adAssetRepository;

    public AdAsset save(AdAsset asset){
        /*
        ModelMapper modelMapper=new ModelMapper();
        AdAssetEntity assetEntity=new AdAssetEntity();
        modelMapper.map(asset,assetEntity);
        AdAssetEntity assetEntity1=adAssetRepository.save(assetEntity);
        AdAsset asset1=new AdAsset();
        modelMapper=new ModelMapper();
        modelMapper.map(assetEntity1,asset1);
        return  asset1;

         */
        return null;
    }
}

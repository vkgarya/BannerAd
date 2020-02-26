package com.coupon.ad.service.impl;

import com.coupon.ad.bean.AdAsset;
import com.coupon.ad.bean.jpa.AdEntity;
import com.coupon.ad.bean.jpa.AdAssetEntity;
import com.coupon.ad.repository.AdAssetRepository;
import com.coupon.ad.repository.AdRepository;
import com.coupon.ad.service.AdAssetService;
import com.coupon.service.mapper.ServiceMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdAssetServiceImpl implements AdAssetService {
    @Resource
    private AdAssetRepository adAssetRepository;


    @Resource
    private AdRepository adRepository;

    @Resource
    private ServiceMapper<AdAsset, AdAssetEntity> assetServiceMapper;

/*
    @Override
    public List<AdAsset> findAllByAdId(Integer id) {
        List<AdAssetEntity>  adAssetEntities = adAssetRepository.findByAdId(id);
        List<AdAsset> assets = new ArrayList<>();
        Iterable<AdAssetEntity> assetEntities = adAssetRepository.findAll();
        List<AdAssetEntity> assetList = StreamSupport.stream(assetEntities.spliterator(), false)
                .collect(Collectors.toList());
        Map<Integer, AdAssetEntity> assetMap = assetList.stream().collect(
                Collectors.toMap(AdAssetEntity::getId, assetEntity -> assetEntity));

        for (AdAssetEntity adAsset : adAssetEntities) {
            assets.add(assetServiceMapper.mapEntityToDTO(assetMap.get(adAsset.getAsset().getId()), AdAsset.class));
        }

        return assets;
    }

    */

    @Override
    @Transactional
    public void saveAll(List<AdAsset> adAssetList){
        List<AdAssetEntity> adAssetEntityList=new ArrayList<AdAssetEntity>();
        for(AdAsset adAsset:adAssetList){
            adAssetEntityList.add(new AdAssetEntity(adAsset));
        }
    }

}

package com.coupon.ad.service.impl;

import com.coupon.ad.bean.AdClickDTO;
import com.coupon.ad.bean.jpa.AdClickEntity;
import com.coupon.ad.repository.AdClickRepository;
import com.coupon.ad.service.AdClickService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdClickServiceImpl implements AdClickService {

    @Resource
    private AdClickRepository adClickRepository;

    @Override
    public AdClickDTO save(AdClickDTO adClickDTO) {
        ModelMapper modelMapper = new ModelMapper();
        AdClickEntity adClickEntity = new AdClickEntity();
        modelMapper.map(adClickDTO, adClickEntity);
        adClickRepository.save(adClickEntity);
        return null;
    }

    public AdClickDTO findByAdTxnId(String adTxnId) {
        AdClickEntity adClickEntity =adClickRepository.findByAdTxnId(adTxnId);
        AdClickDTO adClickDTO=null;
        ModelMapper modelMapper = new ModelMapper();
        if(adClickEntity!=null){
            adClickDTO =new AdClickDTO();
            modelMapper.map(adClickEntity,adClickDTO);
        }
        return  adClickDTO;
}

}

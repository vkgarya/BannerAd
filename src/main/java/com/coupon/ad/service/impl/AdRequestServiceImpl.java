package com.coupon.ad.service.impl;

import com.coupon.ad.bean.AdRequestDTO;
import com.coupon.ad.bean.jpa.AdRequestEntity;
import com.coupon.ad.repository.AdRequestRepository;
import com.coupon.ad.service.AdRequestService;
import com.coupon.service.mapper.ServiceMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdRequestServiceImpl implements AdRequestService {

    @Resource
    private AdRequestRepository adRequestRepository;

    @Resource
    private ServiceMapper<AdRequestDTO,AdRequestEntity  >  adRequestServiceMapper;


    @Override
    public AdRequestDTO save(AdRequestDTO adRequestDTO){
        ModelMapper modelMapper = new ModelMapper();
        AdRequestEntity adRequestEntity=new AdRequestEntity();
        modelMapper.map(adRequestDTO,adRequestEntity);
        adRequestRepository.save(adRequestEntity);
        return  null;
    }
}

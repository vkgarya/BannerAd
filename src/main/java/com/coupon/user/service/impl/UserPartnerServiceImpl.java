package com.coupon.user.service.impl;

import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.Partner;
import com.coupon.user.bean.Role;
import com.coupon.user.bean.jpa.PartnerEntity;
import com.coupon.user.bean.jpa.RoleEntity;
import com.coupon.user.bean.jpa.UserPartnerEntity;
import com.coupon.user.bean.jpa.UserRoleEntity;
import com.coupon.user.repository.PartnerRepository;
import com.coupon.user.repository.UserPartnerRepository;
import com.coupon.user.service.UserPartnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Service
public class UserPartnerServiceImpl implements UserPartnerService {
    @Resource
    private UserPartnerRepository userPartnerRepository;
    @Resource
    private PartnerRepository partnerRepository;

    @Resource
    private ServiceMapper<Partner, PartnerEntity> partnerServiceMapper;

    @Override
    public List<Partner> findAllByUserId(Integer id) {
        List<UserPartnerEntity>  userPartnerEntities = userPartnerRepository.findByUserId(id);
        List<Partner> partners = new ArrayList<>();
        Iterable<PartnerEntity> partnerEntities = partnerRepository.findAll();
        List<PartnerEntity> partnerList = StreamSupport.stream(partnerEntities.spliterator(), false)
                .collect(Collectors.toList());
        Map<Integer, PartnerEntity> partnerMap = partnerList.stream().collect(
                Collectors.toMap(PartnerEntity::getId, partnerEntity -> partnerEntity));

        for (UserPartnerEntity userPartner : userPartnerEntities) {
            partners.add(partnerServiceMapper.mapEntityToDTO(partnerMap.get(userPartner.getPartner().getId()), Partner.class));
        }

        return partners;
    }
}

package com.coupon.partner.service.impl;

import com.coupon.auth.security.CurrentUserProvider;
import com.coupon.auth.security.UserPrincipal;
import com.coupon.exceptions.BadRequestException;
import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.partner.service.PartnerService;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.Partner;
import com.coupon.user.bean.UserDTO;
import com.coupon.user.bean.UserPartnerDTO;
import com.coupon.user.bean.jpa.PartnerEntity;
import com.coupon.user.bean.jpa.UserEntity;
import com.coupon.user.bean.jpa.UserPartnerEntity;
import com.coupon.user.repository.PartnerRepository;
import com.coupon.user.repository.UserPartnerRepository;
import com.coupon.user.repository.UserRepository;
import com.coupon.user.service.UserService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {
    @Resource
    private PartnerRepository partnerRepository;
    @Resource
    private ServiceMapper<Partner, PartnerEntity> partnerMapper;
    @Resource
    private UserPartnerRepository userPartnerRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ServiceMapper<UserDTO, UserEntity> userMapper;
    @Resource
    private UserService userService;

    @Override
    public Partner save(Partner partner) {
        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findByName(partner.getName());

        if(partnerEntityOptional.isPresent()) {
            throw new BadRequestException("Duplicate partner name");
        }

        PartnerEntity partnerEntity = new PartnerEntity();

        partnerMapper.mapDTOToEntity(partner, partnerEntity);
        PartnerEntity savedEntity = partnerRepository.save(partnerEntity);

        return partnerMapper.mapEntityToDTO(savedEntity, Partner.class);
    }

    @Override
    public Partner findByName(String name) {
        Optional<PartnerEntity> optionalPartnerEntity = partnerRepository.findByName(name);

        if(!optionalPartnerEntity.isPresent()) {
            throw new BadRequestException("Invalid Partner Name");
        }

        PartnerEntity partnerEntity = optionalPartnerEntity.get();

        return partnerMapper.mapEntityToDTO(partnerEntity, Partner.class);
    }

    @Override
    public List<Partner> findByStatus(String status){
        List<PartnerEntity> partnerEntities;
        UserPrincipal userPrincipal = CurrentUserProvider.getUser();

        if (userService.hasAdminAccess()) {
            partnerEntities = partnerRepository.findByStatus(status, orderByIdAsc());
        } else {
            List<UserPartnerEntity> userPartnerEntities = userPartnerRepository.findByUserId(userPrincipal.getId());
            List<Integer> ids =  userPartnerEntities.stream().map(ac -> ac.getId()).collect(Collectors.toList());
            partnerEntities = partnerRepository.findByStatusAndIdIn(status, ids, orderByIdAsc());
        }

        List<Partner> partners=new ArrayList<>();
        Partner partner;

        for (PartnerEntity partnerEntity: partnerEntities) {
            partner = partnerMapper.mapEntityToDTO(partnerEntity, Partner.class);
            partners.add(partner);
        }

        return partners;
    }

    private Sort orderByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
                //.and(new Sort(Sort.Direction.ASC, "name"));
    }
    @Override
    public List<Partner> findAll() {
        Iterable<PartnerEntity> partnerEntities = partnerRepository.findAll();
        List<Partner> partners=new ArrayList<>();
        Partner partner;

        for (PartnerEntity partnerEntity: partnerEntities) {
            partner = partnerMapper.mapEntityToDTO(partnerEntity, Partner.class);
            partners.add(partner);
        }

        return partners;
    }

    @Override
    public Partner update(Partner partner) {
        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(partner.getId());

        if(!partnerEntityOptional.isPresent()) {
            throw new BadRequestException("Invalid partner name");
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();

        partnerMapper.mapDTOToEntity(partner, partnerEntity);
        PartnerEntity savedEntity = partnerRepository.save(partnerEntity);

        return partnerMapper.mapEntityToDTO(savedEntity, Partner.class);
    }

    @Override
    public Partner delete(Integer id) {
        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(id);

        if(!partnerEntityOptional.isPresent()) {
            throw new BadRequestException("Invalid partner name");
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();

        partnerEntity.setStatus("inactive");

        PartnerEntity savedEntity = partnerRepository.save(partnerEntity);

        return partnerMapper.mapEntityToDTO(savedEntity, Partner.class);
    }

    @Override
    public void saveUserPartnerMapping(UserPartnerDTO requestBody) {
        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(requestBody.getPartnerId());

        if (!partnerEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("Partner", "id", requestBody.getPartnerId());
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(requestBody.getUserId());

        if (!userEntityOptional.isPresent()) {
            throw new ResourceNotFoundException("User", "id", requestBody.getUserId());
        }

        Optional<UserPartnerEntity> userPartnerEntityOptional = userPartnerRepository.findByUser_idAndPartner_id(requestBody.getUserId(), requestBody.getPartnerId());
        UserPartnerEntity userPartnerEntity = new UserPartnerEntity();

        if ("delete".equals(requestBody.getType())) {
            if (!userPartnerEntityOptional.isPresent()) {
                throw new BadRequestException("Invalid partner id or user id");
            }

            userPartnerEntity = userPartnerEntityOptional.get();
            userPartnerRepository.delete(userPartnerEntity);
        } else {
            if (userPartnerEntityOptional.isPresent()) {
                throw new BadRequestException("Duplicate partner id, user id mapping");
            }

            PartnerEntity partnerEntity = new PartnerEntity();
            partnerEntity.setId(requestBody.getPartnerId());

            UserEntity userEntity = new UserEntity();
            userEntity.setId(requestBody.getUserId());
            userPartnerEntity.setPartner(partnerEntity);
            userPartnerEntity.setUser(userEntity);
            userPartnerRepository.save(userPartnerEntity);
        }
    }

    @Override
    public Partner findById(Integer id) {
        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(id);

        if(!partnerEntityOptional.isPresent()) {
            throw new BadRequestException("Invalid partner name");
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();
        Partner partner = partnerMapper.mapEntityToDTO(partnerEntity, Partner.class);

        List<UserPartnerEntity> userPartnerEntities = userPartnerRepository.findByPartner_id(id);

        List<Integer> userIds = userPartnerEntities.stream().map(up -> up.getUser().getId()).collect(Collectors.toList());
        List<UserEntity> userEntities = userRepository.findByIdIn(userIds);
        List<UserDTO> users = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            users.add(userMapper.mapEntityToDTO(userEntity, UserDTO.class));
        }

        partner.setUsers(users);

        return partner;
    }
}

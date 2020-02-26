package com.coupon.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.Role;
import com.coupon.user.bean.jpa.RoleEntity;
import com.coupon.user.bean.jpa.UserRoleEntity;
import com.coupon.user.repository.RoleRepository;
import com.coupon.user.repository.UserRoleRepository;
import com.coupon.user.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private ServiceMapper<Role, RoleEntity> roleServiceMapper;

    @Override
    public List<Role> findAllByUserId(Integer id) {
        List<UserRoleEntity>  userRoleEntities = userRoleRepository.findByUserId(id);
        List<Role> roles = new ArrayList<>();
        Iterable<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleEntity> roleList = StreamSupport.stream(roleEntities.spliterator(), false)
                .collect(Collectors.toList());
        Map<Integer, RoleEntity> roleMap = roleList.stream().collect(
                Collectors.toMap(RoleEntity::getId, roleEntity -> roleEntity));

        for (UserRoleEntity userRole : userRoleEntities) {
            roles.add(roleServiceMapper.mapEntityToDTO(roleMap.get(userRole.getRole().getId()), Role.class));
        }

        return roles;
    }
}

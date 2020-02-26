package com.coupon.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.auth.payload.AddUserRequest;
import com.coupon.auth.payload.ResetPasswordRequest;
import com.coupon.auth.security.CurrentUserProvider;
import com.coupon.email.EmailDetails;
import com.coupon.email.EmailService;
import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.properties.PropertyManager;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.Partner;
import com.coupon.user.bean.Role;
import com.coupon.user.bean.User;
import com.coupon.user.bean.UserDTO;
import com.coupon.user.bean.UserUpdateDTO;
import com.coupon.user.bean.jpa.PartnerEntity;
import com.coupon.user.bean.jpa.RoleEntity;
import com.coupon.user.bean.jpa.UserEntity;
import com.coupon.user.bean.jpa.UserPartnerEntity;
import com.coupon.user.bean.jpa.UserRoleEntity;
import com.coupon.user.repository.PartnerRepository;
import com.coupon.user.repository.RoleRepository;
import com.coupon.user.repository.UserPartnerRepository;
import com.coupon.user.repository.UserRepository;
import com.coupon.user.repository.UserRoleRepository;
import com.coupon.user.service.PasswordResetTokenService;
import com.coupon.user.service.UserRoleService;
import com.coupon.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserRepository userRepository;
    @Resource
    private ServiceMapper<User, UserEntity> userServiceMapper;
    @Resource
    private ServiceMapper<Role, RoleEntity> roleServiceMapper;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private UserPartnerRepository userPartnerRepository;
    @Resource
    private PartnerRepository partnerRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private ServiceMapper<Partner, PartnerEntity> partnerServiceMapper;

    @Resource
    private EmailService emailService;
    @Resource
    private PasswordResetTokenService passwordResetTokenService;

    @Override

    public User findByEmail(final String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        return userServiceMapper.mapEntityToDTO(userEntity, User.class);
    }

    @Override
    public User findById(final Integer id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        User user = userServiceMapper.mapEntityToDTO(userEntity, User.class);

        return user;
    }

    @Override
    public User findUserWithRolesById(final Integer id) {
        User user = this.findById(id);
        if (user != null) {

            user.setRoles(this.userRoleService.findAllByUserId(id));
            List<UserPartnerEntity> userPartnerEntities = this.userPartnerRepository.findByUserId(id);
            List<PartnerEntity> partnerEntities = new ArrayList<>();
            List<Integer> partnerIds = new ArrayList<>();

            for (UserPartnerEntity userPartnerEntity : userPartnerEntities) {
                partnerIds.add(userPartnerEntity.getPartner().getId());
            }

            if (!partnerIds.isEmpty()) {
                partnerEntities = this.partnerRepository.findByIdIn(partnerIds);
            }

            for (PartnerEntity partnerEntity1 : partnerEntities) {
                user.getPartners().add(partnerServiceMapper.mapEntityToDTO(partnerEntity1, Partner.class));
            }
        }

        return user;
    }

    @Override
    @Transactional
    public User create(final User user) {
        user.setId(null);
        UserEntity userEntity = new UserEntity();
        userServiceMapper.mapDTOToEntity(user, userEntity);
        UserEntity userEntitySaved = userRepository.save(userEntity);
        User savedUser = userServiceMapper.mapEntityToDTO(userEntitySaved, User.class);

        return savedUser;
    }

    @Override
    @Transactional
    public User addUser(final AddUserRequest addUserRequest) {
        User user = new User();
        UserRoleEntity userRoleEntity;
        List<UserRoleEntity> roleEntities = new ArrayList<>();
        RoleEntity roleEntity;
        UserEntity userEntity = new UserEntity();
        user.setEmail(addUserRequest.getEmail());
        user.setPassword(addUserRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = this.create(user);
        userEntity.setId(user.getId());
        for (Integer roleId : addUserRequest.getRole_ids()) {
            userRoleEntity = new UserRoleEntity();
            roleEntity = new RoleEntity();
            roleEntity.setId(roleId);
            userRoleEntity.setRole(roleEntity);
            userRoleEntity.setUser(userEntity);
            roleEntities.add(userRoleEntity);
        }

        userRoleRepository.saveAll(roleEntities);

        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserDetailsById(Integer id) {
        return this.findUserWithRolesById(id);
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO data) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(data.getId());

        if (!optionalUserEntity.isPresent()) {
            throw new ResourceNotFoundException("User", "id", data.getId());
        }

        UserRoleEntity userRoleEntity;
        PartnerEntity partnerEntity;
        List<UserPartnerEntity> userPartnerEntities = new ArrayList<>();
        UserPartnerEntity userPartnerEntity;
        List<UserRoleEntity> roleEntities = new ArrayList<>();
        RoleEntity roleEntity;
        UserEntity userEntity = optionalUserEntity.get();

        List<UserRoleEntity> oldUserRoles = userRoleRepository.findByUserId(data.getId());

        if (!oldUserRoles.isEmpty()) {
            userRoleRepository.deleteAll(oldUserRoles);
        }

        for (Integer roleId : data.getRoles()) {
            userRoleEntity = new UserRoleEntity();
            roleEntity = new RoleEntity();
            roleEntity.setId(roleId);
            userRoleEntity.setRole(roleEntity);
            userRoleEntity.setUser(userEntity);
            roleEntities.add(userRoleEntity);
        }

        if (!roleEntities.isEmpty()) {
            userRoleRepository.saveAll(roleEntities);
        }

        List<UserPartnerEntity> oldUserPartners;
        oldUserPartners = userPartnerRepository.findByUserId(data.getId());

        if (!oldUserPartners.isEmpty()) {
            userPartnerRepository.deleteAll(oldUserPartners);
        }
        for (Integer partnerId : data.getPartners()) {
            partnerEntity = new PartnerEntity();
            userPartnerEntity = new UserPartnerEntity();
            partnerEntity.setId(partnerId);
            userPartnerEntity.setUser(userEntity);
            userPartnerEntity.setPartner(partnerEntity);
            userPartnerEntities.add(userPartnerEntity);
        }

        if (!userPartnerEntities.isEmpty()) {
            userPartnerRepository.saveAll(userPartnerEntities);
        }

        UserDTO response = new UserDTO();

        response.setId(data.getId());

        return response;
    }

    public List<UserDTO> getUsersList() {
        Iterable<UserEntity> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        for (UserEntity userEntity : users) {
            userDTOList.add(userEntity.getUserDTO());
        }
        LOGGER.info("Users list size : " + userDTOList.size());
        ListIterator<UserDTO> userListIterator = userDTOList.listIterator();
        UserDTO userDTO;
        while (userListIterator.hasNext()) {
            userDTO = userListIterator.next();
            List<UserPartnerEntity> userPartnerEntityList = userPartnerRepository.findByUserId(userDTO.getId());
            List<Partner> partnerList = new ArrayList<>();
            for (UserPartnerEntity userPartnerEntity : userPartnerEntityList) {
                PartnerEntity partnerEntity = userPartnerEntity.getPartner();
                Partner partner = partnerServiceMapper.mapEntityToDTO(partnerEntity, Partner.class);
                partnerList.add(partner);
            }
            userDTO.setPartners(partnerList);
            Optional<UserRoleEntity> userRoleEntity = userRoleRepository.findById(userDTO.getId());
            if (userRoleEntity.isPresent()) {
                Role role = userRoleEntity.get().getRole().getRole();
                List<Role> roles = new ArrayList<Role>();
                roles.add(role);
                userDTO.setRoles(roles);
            }
        }
        return userDTOList;
    }



    @Override
    public boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void sendForgotPasswordEmail(final String email) {
        EmailDetails emailDetails = new EmailDetails();
        String token = passwordResetTokenService.create(email).getToken();

        emailDetails.setTitle("Reset Password link for EMPAY");
        emailDetails.setToEmail(new String[] { email });
        emailDetails.setContent(PropertyManager.getWebUrl() + "/reset-password/" + token);

        emailService.sendEmail(emailDetails);
    }

    @Transactional
    private User update(final User user) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(user.getId());
        if (!optionalUserEntity.isPresent()) {
            return null;
        }

        UserEntity userEntity = optionalUserEntity.get();
        userServiceMapper.mapDTOToEntity(user, userEntity);
        UserEntity userEntitySaved = userRepository.save(userEntity);

        return userServiceMapper.mapEntityToDTO(userEntitySaved, User.class);
    }

    @Override
    @Transactional
    public void resetPassword(final ResetPasswordRequest resetPasswordRequest) {
        User user = findByEmail(resetPasswordRequest.getEmail());

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));

        update(user);

        passwordResetTokenService.deleteToken(resetPasswordRequest.getToken());
    }

    @Override
    public boolean hasAdminAccess() {
        Integer userId = CurrentUserProvider.getUserId();

        List<Role> roles = userRoleService.findAllByUserId(userId);

        for (Role role : roles) {
            if (role.getName().equals("admin")) {
                return true;
            }
        }

        return false;
    }
}

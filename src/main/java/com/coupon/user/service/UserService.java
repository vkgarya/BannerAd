package com.coupon.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.coupon.auth.payload.AddUserRequest;
import com.coupon.auth.payload.ResetPasswordRequest;
import com.coupon.user.bean.User;
import com.coupon.user.bean.UserDTO;
import com.coupon.user.bean.UserUpdateDTO;

public interface UserService {
    User findByEmail(String email);

    User findById(Integer id);

    User findUserWithRolesById(Integer id);

    @Transactional
    User create(User user);

    @Transactional
    User addUser(AddUserRequest addUserRequest);

    boolean existsByEmail(String email);

    User getUserDetailsById(Integer id);

    UserDTO updateUser(UserUpdateDTO data);

    List<UserDTO> getUsersList();

    boolean emailExists(String email);

    void sendForgotPasswordEmail(String email);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    boolean hasAdminAccess();
}

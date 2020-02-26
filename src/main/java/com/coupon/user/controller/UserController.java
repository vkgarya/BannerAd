package com.coupon.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.auth.security.CurrentUser;
import com.coupon.auth.security.UserPrincipal;
import com.coupon.user.bean.User;
import com.coupon.user.bean.UserDTO;
import com.coupon.user.bean.UserUpdateDTO;
import com.coupon.user.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("cms/v1/me")
    public UserDTO getCurrentUser(@CurrentUser final UserPrincipal userPrincipal) {
        return new UserDTO(userService.getUserDetailsById(userPrincipal.getId()));
    }

    @PutMapping("cms/v1/update-user")
    public UserDTO getCurrentUser(@RequestBody final UserUpdateDTO data) {
        return userService.updateUser(data);
    }

    @GetMapping("cms/v1/user/{id}")
    public User userDetails(@PathVariable("id") Integer id) {
        return userService.getUserDetailsById(id);
    }

    @GetMapping("cms/v1/users")
    public List<UserDTO> getUsersList(@CurrentUser final UserPrincipal userPrincipal) {
        return userService.getUsersList();
    }
}

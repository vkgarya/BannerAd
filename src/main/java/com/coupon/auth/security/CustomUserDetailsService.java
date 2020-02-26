package com.coupon.auth.security;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.user.bean.User;
import com.coupon.user.service.UserService;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(final Integer id) throws ResourceNotFoundException {
        User user = userService.findById(id);

        return UserPrincipal.create(user);
    }
}

package com.coupon.service;

import com.coupon.bean.CreateUserRequest;
import com.coupon.bean.EndUserResponse;

public interface EndUserService {
    EndUserResponse createUser(CreateUserRequest requestBody);

    EndUserResponse updateUser(CreateUserRequest requestBody);

    EndUserResponse getUser(String userId);
}

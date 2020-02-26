package com.coupon.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.auth.TokenProvider;
import com.coupon.auth.payload.AddUserRequest;
import com.coupon.auth.payload.AddUserResponse;
import com.coupon.auth.payload.AuthResponse;
import com.coupon.auth.payload.LoginRequest;
import com.coupon.auth.payload.ResetPasswordRequest;
import com.coupon.bean.StatusDTO;
import com.coupon.constants.CmsStatusCodes;
import com.coupon.tracker.service.TrackerService;
import com.coupon.user.service.UserService;
import com.coupon.utils.InputValidationUtil;

@RestController
@RequestMapping("/")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TokenProvider tokenProvider;
    @Resource
    private UserService userService;
    @Resource
    private TrackerService trackerService;

    @PostMapping("cms/v1/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        trackerService.save(httpServletRequest, "login");
        LOGGER.info("/cms/v1/create-ad : " + loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("cms/v1/add-user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final AddUserRequest addUserRequest) {

        ResponseEntity<?> errorObj=InputValidationUtil.addUserRequest(addUserRequest);
        if(errorObj!=null)
            return errorObj;

        if (userService.existsByEmail(addUserRequest.getEmail())) {
            LOGGER.info("add-user : Email already exist : "+addUserRequest.getEmail());
            StatusDTO statusDTO= CmsStatusCodes.VALUES.get(CmsStatusCodes.EMAIL_ALREADY_EXIST);
            return ResponseEntity.ok(new AddUserResponse(addUserRequest.getTxn_id(),statusDTO));
        }
        LOGGER.info("cms/v1/add-user : "+addUserRequest);
        userService.addUser(addUserRequest);

        StatusDTO statusDTO=CmsStatusCodes.VALUES.get(CmsStatusCodes.SUCCESS);
        return ResponseEntity.ok(new AddUserResponse(addUserRequest.getTxn_id(),statusDTO));
    }

    @PostMapping("cms/v1/forgot-password/{email}")
    @PreAuthorize("@userService.emailExists(#email)")
    @ResponseBody
    public void forgotPassword(@PathVariable("email") final String email) {
        userService.sendForgotPasswordEmail(email);
    }

    @PostMapping("cms/v1/reset-password")
    @PreAuthorize("@resetPasswordTokenService.existsByTokenAndEmail(#resetPasswordRequest.token, #resetPasswordRequest.email)")
    @ResponseBody
    public void resetPassword(@Valid @RequestBody final ResetPasswordRequest resetPasswordRequest) {
        userService.resetPassword(resetPasswordRequest);
    }
}

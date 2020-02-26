package com.coupon.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coupon.bean.CartRequest;
import com.coupon.bean.CartResponse;
import com.coupon.bean.ConversionRequest;
import com.coupon.bean.ConversionResponse;
import com.coupon.bean.CreateUserRequest;
import com.coupon.bean.EndUserResponse;
import com.coupon.service.CartService;
import com.coupon.service.ConversionService;
import com.coupon.service.EndUserService;
import com.coupon.tracker.service.TrackerService;

@RestController
public class ApiController {
	@Resource
	private CartService cartService;
	@Resource
	private ConversionService conversionService;
	@Resource
	private EndUserService endUserService;
	@Resource
	private TrackerService trackerService;

	@PostMapping("/api/checkout")
	CartResponse requestInfo(@RequestBody final CartRequest requestBody, HttpServletRequest request) {
		trackerService.save(request, "checkout");
		return cartService.getCartResponse(requestBody);
	}

	@PostMapping("/api/conversion")
	ConversionResponse saveCart(@RequestBody final ConversionRequest requestBody, HttpServletRequest request) {
		trackerService.save(request, "conversion");
		return conversionService.saveConversions(requestBody);
	}

	@PostMapping("/api/user")
	EndUserResponse createUser(@RequestBody final CreateUserRequest requestBody, HttpServletRequest request) {
		trackerService.save(request, "create-user");
		return endUserService.createUser(requestBody);
	}

	@PutMapping("/api/user")
	EndUserResponse updateUser(@RequestBody final CreateUserRequest requestBody, HttpServletRequest request) {
		trackerService.save(request, "update-user");
		return endUserService.updateUser(requestBody);
	}

	@GetMapping("/api/user/{userId}")
	EndUserResponse getUser(@PathVariable("userId")final String userId, HttpServletRequest request) {
		trackerService.save(request, "get-user");
		return endUserService.getUser(userId);
	}
}

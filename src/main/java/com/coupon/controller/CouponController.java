package com.coupon.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coupon.bean.CouponDTO;
import com.coupon.bean.CouponDataDTO;
import com.coupon.bean.CouponStatus;
import com.coupon.bean.Response;
import com.coupon.service.CouponService;
import com.coupon.tracker.service.TrackerService;

@RestController
public class CouponController {
    @Resource
    private CouponService couponService;
    @Resource
    private TrackerService trackerService;

    @RequestMapping(value = "/cms/v1/coupon/create", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    CouponDTO createCoupon(@RequestPart(value = "offer_file", required = false) final MultipartFile offerFile,
                           @RequestPart(value = "product_restrictions_include", required = false) final MultipartFile productsIncluded,
                           @RequestPart(value = "product_restrictions_exclude", required = false) final MultipartFile productsExcluded,
                           @RequestPart(value = "user_restrictions_include", required = false) final MultipartFile usersIncluded,
                           @RequestPart(value = "user_restrictions_exclude", required = false) final MultipartFile usersExcluded,
                           @RequestPart("data") final CouponDataDTO data, HttpServletRequest request) throws IOException {
        trackerService.save(request, "create-coupon");
        return couponService.create(offerFile, productsIncluded, productsExcluded, usersIncluded, usersExcluded, data);
    }

    @GetMapping("/cms/v1/coupons")
    public List<CouponDTO> getCoupons(HttpServletRequest request) {
        trackerService.save(request, "active-coupons");
        return couponService.getActiveCoupons();
    }

    @GetMapping("/cms/v1/coupon/{id}")
    public CouponDataDTO getCouponDetails(@PathVariable("id") final Integer id, HttpServletRequest request) {
        trackerService.save(request, "coupon-details");
        return couponService.getCouponDetails(id);
    }

    @RequestMapping(value = "/cms/v1/coupon/edit", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    @PreAuthorize("@userService.hasAdminAccess()")
    CouponDTO editCoupon(@RequestPart(value = "offer_file", required = false) final MultipartFile offerFile,
                         @RequestPart(value = "product_restrictions_include", required = false) final MultipartFile productsIncluded,
                         @RequestPart(value = "product_restrictions_exclude", required = false) final MultipartFile productsExcluded,
                         @RequestPart(value = "user_restrictions_include", required = false) final MultipartFile usersIncluded,
                         @RequestPart(value = "user_restrictions_exclude", required = false) final MultipartFile usersExcluded,
                         @RequestPart("data") final CouponDataDTO data, HttpServletRequest request) throws IOException {
        trackerService.save(request, "edit-coupon");
        return couponService.edit(offerFile, productsIncluded, productsExcluded, usersIncluded, usersExcluded, data);
    }

    @DeleteMapping("/cms/v1/coupon/{id}")
    @PreAuthorize("@userService.hasAdminAccess()")
    public Response deleteCoupon(@PathVariable("id") final Integer id, HttpServletRequest request) {
        trackerService.save(request, "delete-coupon");
        couponService.delete(id);
        Response response = new Response();

        response.setCode(2000);
        response.setMessage("Coupon deleted.");

        return response;
    }

    @RequestMapping(value = "/cms/v1/coupon/update-status", method = RequestMethod.POST, consumes = {"application/json"})
    @PreAuthorize("@userService.hasAdminAccess()")
    public Response updateStatus(@RequestBody final CouponStatus data, HttpServletRequest request) {
        trackerService.save(request, "update-coupon-status");
        couponService.updateStatus(data);
        Response response = new Response();

        response.setCode(2000);
        response.setMessage("Coupon updated successfully.");

        return response;
    }

    @GetMapping(value = "/cms/v1/valid-coupons")
    @ResponseBody
    public List<CouponDTO> getValidCoupons(@RequestParam String userId,
                                           @RequestParam(required = false) String category) {
        return couponService.getValidCoupons(userId, category);
    }
}

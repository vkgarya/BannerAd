package com.coupon.ad.controller;

import com.coupon.ad.bean.*;
import com.coupon.ad.constants.AdClickType;
import com.coupon.ad.constants.AdStatusCodes;
import com.coupon.ad.payload.AdRequest;
import com.coupon.ad.payload.AdResponse;
import com.coupon.ad.payload.AdResponseCMS;
import com.coupon.ad.payload.CreateAdRequest;
import com.coupon.ad.service.*;
import com.coupon.bean.StatusDTO;
import com.coupon.constants.CmsStatusCodes;
import com.coupon.properties.PropertyManager;
import com.coupon.utils.CampaignUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class AdController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdController.class);
    @Resource
    private AdService adService;

    @Resource
    private AdRequestService adRequestService;

    @Autowired
    private HttpServletRequest request;

    @Resource
    private AdClickService adClickService;

    @Resource
    private AdAssetService adAssetService;

    @Resource
    private AssetService assetService;


    @PostMapping("/v1/ad-request")
    public AdResponse adRequest(@RequestBody final AdRequest requestBody) {
        LOGGER.info("/v1/ad-request : " + requestBody);
        return adService.adRequest(requestBody, request);

    }

    @PostMapping(value = "/cms/v1/create-ad", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AdResponse createAd(@RequestPart(value = "data") final CreateAdRequest createAdRequest, @RequestPart(value = "files") final MultipartFile[] files) {
        LOGGER.info("/cms/v1/create-ad : " + createAdRequest);
        return adService.createAd(createAdRequest, files);

    }

    @GetMapping("/cms/v1/ads/{ad_type}")
    public AdResponseCMS adList(@PathVariable("ad_type") final String ad_type) {
        LOGGER.info("/cms/v1/ads/" + ad_type);
        return adService.adList(ad_type);
    }

    @DeleteMapping("/cms/v1/ad/{ad_type}/{id}")
    public AdResponseCMS delete(@PathVariable("ad_type") final String ad_type, @PathVariable("id") final Integer id) {
        LOGGER.info("/cms/v1/ad/" + ad_type + "/" + id + " : " + id);
        return adService.delete(id, ad_type);
    }

    @GetMapping("/cms/v1/ad/{ad_type}/{id}")
    public AdResponseCMS getAd(@PathVariable("ad_type") final String ad_type, @PathVariable("id") final Integer id) {
        LOGGER.info("/cms/v1/ad/" + ad_type + "/" + id + " : " + id);
        return adService.getAd(id, ad_type);
    }
}

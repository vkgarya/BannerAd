package com.coupon.ad.service;

import com.coupon.ad.bean.Ad;
import com.coupon.ad.payload.AdRequest;
import com.coupon.ad.payload.AdResponse;
import com.coupon.ad.payload.AdResponseCMS;
import com.coupon.ad.payload.CreateAdRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdService {
    Ad findById(Integer id);

    List<Ad> findAllActive(String type);

    Ad save(Ad ad);

    List<Ad> findAll(String type);

    AdResponse createAd(final CreateAdRequest createAdRequest, final MultipartFile[] files);

    AdResponse adRequest(final AdRequest requestBody, HttpServletRequest request);

    AdResponseCMS adList(final String ad_type);

    AdResponseCMS delete(Integer id, String ad_type);

    AdResponseCMS getAd(Integer id, String ad_type);
}

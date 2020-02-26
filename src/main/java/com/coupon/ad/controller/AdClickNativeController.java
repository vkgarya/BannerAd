package com.coupon.ad.controller;

import com.coupon.ad.bean.Ad;
import com.coupon.ad.bean.AdClickDTO;
import com.coupon.ad.constants.AdStatusCodes;
import com.coupon.ad.payload.AdResponse;
import com.coupon.ad.service.AdClickService;
import com.coupon.ad.service.AdService;
import com.coupon.bean.StatusDTO;
import com.coupon.tracker.service.TrackerService;
import com.coupon.utils.CampaignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class AdClickNativeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdClickNativeController.class);
    @Resource
    private AdService adService;

    @Resource
    private AdClickService adClickService;

    @Autowired
    private HttpServletRequest request;

    @Resource
    private TrackerService trackerService;

    @GetMapping("/v1/ad-click-native-request/{id}")
    public AdResponse redirectWithUsingRedirectView(@PathVariable("id") final String id, HttpServletRequest httpServletRequest) {
        trackerService.save(httpServletRequest, "ad-click-native-request");
        LOGGER.info(" Request to /v1/ad-click-native-request/" + id);
        AdResponse adResponse = null;
        StatusDTO statusDTO;
        AdClickDTO adClickDTO = adClickService.findByAdTxnId(id);
        if (adClickDTO != null) {
            adClickDTO.setRemoteip(CampaignUtil.getClientIP(request));
            adClickDTO.setClickCount(adClickDTO.getClickCount() + 1);
            adClickDTO.setClickDate(new Date());
            adClickDTO.setClickTime(new Date());
            adClickService.save(adClickDTO);
            Ad ad = adService.findById(adClickDTO.getAdId());
            if (ad != null) {
                statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.SUCCESS);
                adResponse = new AdResponse(id, statusDTO, null);
                adResponse.setLink(ad.getClick_url());
            }
        }
        if(adResponse==null){
            statusDTO = AdStatusCodes.VALUES.get(AdStatusCodes.INVALID_FIELD_VALUES);
            adResponse = new AdResponse(id, statusDTO, null);
        }
        return adResponse;
    }
}

package com.coupon.ad.controller;

import com.coupon.ad.bean.Ad;
import com.coupon.ad.bean.AdClickDTO;
import com.coupon.ad.service.AdClickService;
import com.coupon.ad.service.AdRequestService;
import com.coupon.ad.service.AdService;
import com.coupon.tracker.service.TrackerService;
import com.coupon.utils.CampaignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
public class AdClickController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdClickController.class);

    @Resource
    private AdRequestService adRequestService;

    @Resource
    private AdClickService adClickService;

    @Resource
    private AdService adService;

    @Autowired
    private HttpServletRequest request;

    @Resource
    private TrackerService trackerService;


    @GetMapping("/v1/ad-click-request/{id}")
    public RedirectView redirectWithUsingRedirectView(@PathVariable("id")final String id, HttpServletRequest request) {
        trackerService.save(request, "ad-click-request");
        LOGGER.info(" Request to /v1/ad-click-request/"+id);
        String redirectionURl="/";
        AdClickDTO adClickDTO=adClickService.findByAdTxnId(id);
        if(adClickDTO!=null) {
            adClickDTO.setRemoteip(CampaignUtil.getClientIP(request));
            adClickDTO.setClickCount(adClickDTO.getClickCount()+1);
            adClickDTO.setClickDate(new Date());
            adClickDTO.setClickTime(new Date());
            adClickService.save(adClickDTO);

            Ad ad = adService.findById(adClickDTO.getAdId());
            if(ad!=null) {
                redirectionURl = ad.getClick_url();
            }

        }
        return new RedirectView(redirectionURl);
    }
}

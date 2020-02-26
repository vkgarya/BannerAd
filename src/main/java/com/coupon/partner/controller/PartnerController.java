package com.coupon.partner.controller;

import com.coupon.ad.payload.AdRequest;
import com.coupon.ad.payload.AdResponse;
import com.coupon.bean.Response;
import com.coupon.bean.StatusDTO;
import com.coupon.constants.CmsStatusCodes;
import com.coupon.partner.constants.Status;
import com.coupon.partner.payload.AddPartnerRequest;
import com.coupon.partner.payload.AllPartnersRequest;
import com.coupon.partner.service.PartnerService;
import com.coupon.tracker.service.TrackerService;
import com.coupon.user.bean.Partner;
import com.coupon.user.bean.UserPartnerDTO;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
public class PartnerController {
    @Resource
    private PartnerService partnerService;
    @Resource
    private TrackerService trackerService;

    @PostMapping("/cms/v1/partner")
    public Partner addPartner(@RequestBody final Partner requestBody, HttpServletRequest request) {
        trackerService.save(request, "create-partner");
        return partnerService.save(requestBody);
    }

    @PutMapping("/cms/v1/partner")
    public Partner updatePartner(@RequestBody final Partner requestBody, HttpServletRequest request) {
        trackerService.save(request, "update-partner");
        return partnerService.update(requestBody);
    }

    @DeleteMapping("/cms/v1/partner/{id}")
    public Partner deletePartner(@PathVariable("id")final Integer id, HttpServletRequest request) {
        trackerService.save(request, "delete-partner");
        return partnerService.delete(id);
    }

    @GetMapping("/cms/v1/partners/{status}")
    public List<Partner> partnersByStatus(@PathVariable("status")final String status, HttpServletRequest request) {
        trackerService.save(request, "get-partners");
        return partnerService.findByStatus(status);
    }

    @GetMapping("/cms/v1/partner/{id}")
    public Partner partnerById(@PathVariable("id")final Integer id, HttpServletRequest request) {
        trackerService.save(request, "get-partner");
        return partnerService.findById(id);
    }

    @GetMapping("/cms/v1/partners")
    public List<Partner> allPartners() {
        return partnerService.findAll();
    }

    @PostMapping("/cms/v1/user-partner")
    public Response updatePartnerUser(@RequestBody final UserPartnerDTO requestBody, HttpServletRequest request) {
        trackerService.save(request, "create-user-partner-relation");
        partnerService.saveUserPartnerMapping(requestBody);

        Response response = new Response();
        response.setCode(2000);
        response.setMessage("Success");

        return response;
    }
}

package com.coupon.tracker.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.coupon.tracker.bean.jpa.TrackerEntity;
import com.coupon.tracker.repository.TrackerRepository;
import com.coupon.tracker.service.TrackerService;
import com.coupon.utils.TimeUtil;

@Service
public class TrackerServiceImpl implements TrackerService {
    @Resource
    private TrackerRepository trackerRepository;

    @Override
    public void save(HttpServletRequest request, String pageName) {
        String ip = getClientIpAddr(request);
        TrackerEntity trackerEntity = new TrackerEntity();

        trackerEntity.setIp(ip);
        trackerEntity.setPageName(pageName);
        trackerEntity.setUserAgent(request.getHeader("User-Agent"));
        trackerEntity.setQueryString(request.getQueryString());
        trackerEntity.setCreatedAt(TimeUtil.getCurrentUTCTime());

        trackerRepository.save(trackerEntity);
    }

    private String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

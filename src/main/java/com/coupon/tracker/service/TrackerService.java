package com.coupon.tracker.service;

import javax.servlet.http.HttpServletRequest;

public interface TrackerService {
    void save(HttpServletRequest request, String pageName);
}

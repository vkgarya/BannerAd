package com.coupon.service;

import com.coupon.bean.ConversionRequest;
import com.coupon.bean.ConversionResponse;

public interface ConversionService {
    ConversionResponse saveConversions(ConversionRequest data);
}

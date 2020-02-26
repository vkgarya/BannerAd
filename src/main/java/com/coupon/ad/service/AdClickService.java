package com.coupon.ad.service;

import com.coupon.ad.bean.AdClickDTO;

public interface AdClickService {
    AdClickDTO save(AdClickDTO adClickDTO);
    AdClickDTO findByAdTxnId(String adTxnId);
}

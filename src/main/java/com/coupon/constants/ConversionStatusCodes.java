package com.coupon.constants;

import java.util.HashMap;
import java.util.Map;

import com.coupon.bean.StatusDTO;

public class ConversionStatusCodes {
    public static final Map<Integer, StatusDTO> VALUES;

    static {
        VALUES = new HashMap<>();
        VALUES.put(2000, new StatusDTO(2000, "success", "processed successfully"));
        VALUES.put(3100, new StatusDTO(3100, "error", "Invalid transaction id"));
        VALUES.put(3101, new StatusDTO(3101, "error", "Transaction status missing"));
        VALUES.put(3102, new StatusDTO(3102, "error", "Transaction id missing"));
        VALUES.put(3103, new StatusDTO(3103, "error", "Duplicate Conversion"));
        VALUES.put(3104, new StatusDTO(3104, "error", "Invalid referral bonus"));
        VALUES.put(3105, new StatusDTO(3105, "error", "Invalid coupons found "));
    }

    public static final Integer SUCCESS = 2000;
    public static final Integer INVALID_TRANSACTION_ID = 3100;
    public static final Integer TRANSACTION_STATUS_MISSING = 3101;
    public static final Integer TRANSACTION_ID_MISSING = 3102;
    public static final Integer DUPLICATE_TRANSACTION = 3103;
    public static final Integer INVALID_REFERRAL_BONUS = 3104;
    public static final Integer INVALID_COUPON_CODE = 3105;
}

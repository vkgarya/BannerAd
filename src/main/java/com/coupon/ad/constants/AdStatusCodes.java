package com.coupon.ad.constants;

import com.coupon.bean.StatusDTO;

import java.util.HashMap;
import java.util.Map;

public class AdStatusCodes {
    public static final Map<Integer, StatusDTO> VALUES;

    static {
        VALUES = new HashMap<>();
        VALUES.put(1000, new StatusDTO(1000, "success", "processed successfully"));
        VALUES.put(1001, new StatusDTO(1001, "error", "Invalid field values"));
        VALUES.put(1002, new StatusDTO(1002, "error", "Mandatory fields missing"));
        VALUES.put(1003, new StatusDTO(1003, "error", "no ads"));
    }

    public static final Integer SUCCESS = 1000;
    public static final Integer INVALID_FIELD_VALUES = 1001;
    public static final Integer INVALID_TXN_ID = 1002;
    public static final Integer NO_ADS = 1003;
}

package com.coupon.constants;

import com.coupon.bean.StatusDTO;

import java.util.HashMap;
import java.util.Map;

public class CmsStatusCodes {
    public static final Map<Integer, StatusDTO> VALUES;

    static {
        VALUES = new HashMap<>();
        VALUES.put(4000, new StatusDTO(4000, "success", "processed successfully"));
        VALUES.put(5001, new StatusDTO(5001, "error", "mandatory parameters are missing"));
        VALUES.put(5002, new StatusDTO(5002, "error", "email already exist"));
        VALUES.put(5003, new StatusDTO(5003, "error", "invalid credentials"));
        VALUES.put(5004, new StatusDTO(5004, "error", "partner already exist"));
        VALUES.put(5005, new StatusDTO(5004, "error", "no records found"));
    }
    public static final Integer SUCCESS = 4000;
    public static final Integer INVALID_FIELD_VALUES = 5001;
    public static final Integer EMAIL_ALREADY_EXIST = 5002;
    public static final Integer PARTNER_ALREADY_EXIST = 5004;
    public static final Integer NO_RECORDS_EXIST = 5005;


}

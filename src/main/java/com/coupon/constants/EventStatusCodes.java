package com.coupon.constants;

import java.util.HashMap;
import java.util.Map;

import com.coupon.bean.StatusDTO;

public class EventStatusCodes {
    public static final Map<Integer, StatusDTO> VALUES;

    static {
        VALUES = new HashMap<>();
        VALUES.put(2000, new StatusDTO(2000, "success", "processed successfully"));
        VALUES.put(3100, new StatusDTO(3100, "error", "Invalid field values"));
        VALUES.put(3101, new StatusDTO(3101, "error", "Invalid event code"));
        VALUES.put(3102, new StatusDTO(3102, "error", "Mandatory field, event code missing"));
        VALUES.put(3103, new StatusDTO(3103, "error", "Mandatory event field %fields% missing"));
        VALUES.put(3104, new StatusDTO(3104, "error", "Duplicate transaction id"));
    }

    public static final Integer SUCCESS = 2000;
    public static final Integer INVALID_FIELD_VALUES = 3100;
    public static final Integer INVALID_EVENT_CODE = 3101;
    public static final Integer EVENT_CODE_MISSING = 3102;
    public static final Integer MANDATORY_EVENT_FIELDS_MISSING = 3103;
    public static final Integer DUPLICATE_TRANSACTION_ID = 3104;
    public static final Integer DUPLICATE_EVENT_CODE = 3105;
}

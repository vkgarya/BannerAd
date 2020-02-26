package com.coupon.utils;

import com.coupon.auth.payload.AddUserRequest;
import com.coupon.auth.payload.AddUserResponse;
import com.coupon.bean.StatusDTO;
import com.coupon.constants.CmsStatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.thymeleaf.util.StringUtils;

public class InputValidationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputValidationUtil.class);
    public static ResponseEntity<?> addUserRequest(final AddUserRequest addUserRequest){
        if (addUserRequest==null) {
            LOGGER.info("add-user : Invalid input Parameters : "+addUserRequest.toString());
            StatusDTO statusDTO= CmsStatusCodes.VALUES.get(5001);
            return ResponseEntity.ok(new AddUserResponse("",statusDTO));
        }

        if (StringUtils.isEmpty(addUserRequest.getTxn_id())) {
            LOGGER.info("add-user : Invalid txn_id : "+addUserRequest.toString());
            StatusDTO statusDTO= CmsStatusCodes.VALUES.get(5001);
            return ResponseEntity.ok(new AddUserResponse("",statusDTO));
        }

        if (addUserRequest.getRole_ids()==null || addUserRequest.getRole_ids().isEmpty()) {
            LOGGER.info("add-user : Invalid role ids : "+addUserRequest.toString());
            StatusDTO statusDTO= CmsStatusCodes.VALUES.get(5001);
            return ResponseEntity.ok(new AddUserResponse(addUserRequest.getTxn_id(),statusDTO));
        }

        return null;
    }



}

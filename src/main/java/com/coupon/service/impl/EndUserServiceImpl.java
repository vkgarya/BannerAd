package com.coupon.service.impl;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coupon.bean.CreateUserRequest;
import com.coupon.bean.EndUserDTO;
import com.coupon.bean.EndUserResponse;
import com.coupon.bean.jpa.EndUserEntity;
import com.coupon.repository.EndUserRepository;
import com.coupon.service.EndUserService;

@Service
public class EndUserServiceImpl implements EndUserService {
    @Resource
    private EndUserRepository endUserRepository;


    @Override
    public EndUserResponse createUser(CreateUserRequest requestBody) {
        Optional<EndUserEntity> temp = endUserRepository.findByUserId(requestBody.getUser_id());
        EndUserResponse response = new EndUserResponse();

        if (temp.isPresent()) {
            response.setMessage("User Already Exists");
            response.setStatus("error");
            response.setStatus_code(3000);

            return response;
        }

        if (requestBody.getReferral_code() != null) {
            temp = endUserRepository.findByUserReferrerCode(requestBody.getReferral_code());

            if (!temp.isPresent()) {
                response.setMessage("Invalid Referral Code");
                response.setStatus("error");
                response.setStatus_code(3001);

                return response;
            }
        }

        EndUserEntity entity = new EndUserEntity(requestBody);
        entity.setUserReferrerCode(createReferralCode());

        EndUserEntity savedEntity = endUserRepository.save(entity);

        response.setMessage("User Created");
        response.setStatus("success");
        response.setStatus_code(2000);
        response.setUser_details(new EndUserDTO(savedEntity));

        return response;
    }

    @Override
    public EndUserResponse updateUser(CreateUserRequest requestBody) {
        Optional<EndUserEntity> optionalEndUserEntity = endUserRepository.findByUserId(requestBody.getUser_id());
        EndUserResponse response = new EndUserResponse();

        if (!optionalEndUserEntity.isPresent()) {
            response.setMessage("Invalid User Id");
            response.setStatus("error");
            response.setStatus_code(3000);

            return response;
        }

        EndUserEntity endUserEntity = optionalEndUserEntity.get();
        EndUserEntity entity = new EndUserEntity(requestBody);
        entity.setUserReferrerCode(endUserEntity.getUserReferrerCode());
        entity.setReferrerCode(endUserEntity.getReferrerCode());
        entity.setId(endUserEntity.getId());

        EndUserEntity savedEntity = endUserRepository.save(entity);

        response.setMessage("User Created");
        response.setStatus("success");
        response.setStatus_code(2000);
        response.setUser_details(new EndUserDTO(savedEntity));

        return response;
    }

    @Override
    public EndUserResponse getUser(String userId) {
        Optional<EndUserEntity> temp = endUserRepository.findByUserId(userId);
        EndUserResponse response = new EndUserResponse();

        if (!temp.isPresent()) {
            response.setMessage("Invalid User Id");
            response.setStatus("error");
            response.setStatus_code(3000);

            return response;
        }

        response.setMessage("User Retrieved");
        response.setStatus("success");
        response.setStatus_code(2000);
        response.setUser_details(new EndUserDTO(temp.get()));

        return response;
    }

    private String createReferralCode() {
        byte[] array = new byte[256];
        int n = 8;
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }
}

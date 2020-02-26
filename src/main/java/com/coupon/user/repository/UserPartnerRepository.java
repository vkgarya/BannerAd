package com.coupon.user.repository;

import com.coupon.user.bean.jpa.UserPartnerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import java.util.Optional;

public interface UserPartnerRepository extends PagingAndSortingRepository<UserPartnerEntity, Integer> {
    List<UserPartnerEntity> findByUserId(Integer id);

    Optional<UserPartnerEntity> findByUser_idAndPartner_id(Integer userId, Integer partnerId);

    List<UserPartnerEntity> findByPartner_id(Integer id);
}

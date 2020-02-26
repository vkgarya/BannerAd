package com.coupon.user.repository;

import com.coupon.user.bean.jpa.PartnerEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends PagingAndSortingRepository<PartnerEntity, Integer> {
    List<PartnerEntity> findByIdIn(List<Integer> ids);
    Optional<PartnerEntity> findByName(String name);
    List<PartnerEntity> findByStatus(String status,Sort sort);

    List<PartnerEntity> findByStatusAndIdIn(String status, List<Integer> ids, Sort orderByIdAsc);
}

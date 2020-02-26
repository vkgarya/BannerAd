package com.coupon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coupon.bean.CartItem;
import com.coupon.bean.CartRequest;
import com.coupon.bean.ConversionRequest;
import com.coupon.bean.ConversionResponse;
import com.coupon.bean.Coupon;
import com.coupon.bean.Referral;
import com.coupon.bean.UserData;
import com.coupon.bean.jpa.CartDataEntity;
import com.coupon.bean.jpa.CartItemEntity;
import com.coupon.bean.jpa.ConversionDataCouponMappingEntity;
import com.coupon.bean.jpa.ConversionDataEntity;
import com.coupon.bean.jpa.CouponEntity;
import com.coupon.bean.jpa.EndUserEntity;
import com.coupon.bean.jpa.ReferralBonusMappingEntity;
import com.coupon.bean.jpa.ReferralTransactionEntity;
import com.coupon.constants.ConversionStatusCodes;
import com.coupon.constants.ReferralCouponType;
import com.coupon.constants.ReferralUserType;
import com.coupon.constants.Status;
import com.coupon.constants.TransactionType;
import com.coupon.repository.CartDataRepository;
import com.coupon.repository.CartItemRepository;
import com.coupon.repository.ConversionDataCouponMappingRepository;
import com.coupon.repository.ConversionDataRepository;
import com.coupon.repository.CouponRepository;
import com.coupon.repository.EndUserRepository;
import com.coupon.repository.ReferralBonusMappingRepository;
import com.coupon.repository.ReferralCouponRepository;
import com.coupon.repository.ReferralTransactionRepository;
import com.coupon.service.CartService;
import com.coupon.service.ConversionService;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.bean.jpa.UserEntity;
import com.coupon.utils.TimeUtil;

@Service
@Transactional
public class ConversionServiceImpl implements ConversionService {
    @Resource
    private ConversionDataRepository conversionDataRepository;
    @Resource
    private CartDataRepository cartDataRepository;
    @Resource
    private ReferralCouponRepository referralCouponRepository;
    @Resource
    private ReferralTransactionRepository referralTransactionRepository;
    @Resource
    private EndUserRepository endUserRepository;
    @Resource
    private ReferralBonusMappingRepository referralBonusMappingRepository;
    @Resource
    private CartService cartService;
    @Resource
    private CartItemRepository cartItemRepository;
    @Resource
    private ServiceMapper<CartItem, CartItemEntity> cartItemEntityCartItemServiceMapper;
    @Resource
    private ConversionDataCouponMappingRepository conversionDataCouponMappingRepository;
    @Resource
    private CouponRepository couponRepository;

    @Override
    public ConversionResponse saveConversions(ConversionRequest data) {
        if (data.getTxn_id() == null) {
            return new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.INVALID_TRANSACTION_ID), data.getTxn_id());
        }

        if (data.getStatus() == null) {
            return new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.TRANSACTION_STATUS_MISSING), data.getTxn_id());
        }

        Integer cartDataId = cartDataRepository.findLatestIdByTxnId(data.getTxn_id());

        if (cartDataId == null) {
            return new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.INVALID_TRANSACTION_ID), data.getTxn_id());
        }

        CartDataEntity cartDataEntity = cartDataRepository.findById(cartDataId).get();
        List<Integer> appliedCouponIds = new ArrayList<>();
        List<Coupon> appliedCoupons = new ArrayList<>();

        if (data.getStatus() == Status.success) {
            CartRequest cartRequest = getCartRequest(cartDataEntity);

            List<Referral> referrals = cartService.getReferrals(cartRequest);
            List<Coupon> coupons = cartService.getCoupons(cartRequest);
            Map<Integer, Coupon> couponIdMap = new HashMap<>();

            List<Integer> validCouponIds = new ArrayList<>();

            for (Coupon coupon : coupons) {
                if (!coupon.getDisabled()) {
                    validCouponIds.add(coupon.getCouponId());

                    couponIdMap.put(coupon.getCouponId(), coupon);
                }
            }

            for (Referral referral : referrals) {
                validCouponIds.add(referral.getCouponId());
            }

            appliedCouponIds = data.getCoupon_codes();

            List<Integer> invalidCoupons = new ArrayList<>();

            for (int i = 0; i < appliedCouponIds.size(); i++) {
                Integer couponId = appliedCouponIds.get(i);

                if (validCouponIds.indexOf(couponId) == -1) {
                    invalidCoupons.add(couponId);
                }

                if (couponIdMap.get(couponId) != null) {
                    appliedCoupons.add(couponIdMap.get(couponId));
                }
            }

            if (invalidCoupons.size() > 0) {
                ConversionResponse response = new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.INVALID_COUPON_CODE), data.getTxn_id());

                response.setDescription(response.getDescription() + " " + invalidCoupons.toString());

                return response;
            }
        }

        List<ConversionDataEntity> conversions = conversionDataRepository.findByTxnId(data.getTxn_id());
        Optional<ConversionDataEntity> conversionDataEntityOptional = conversionDataRepository.findFirstByUserIdOrderByIdDesc(cartDataEntity.getUserId());

        if (!conversions.isEmpty() && conversions.get(0).getStatus().equals(data.getStatus())) {
            return new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.DUPLICATE_TRANSACTION), data.getTxn_id());
        }

        ReferralTransactionEntity referralTransactionEntity1 = new ReferralTransactionEntity();

        if (data.getRewards_used() != null && data.getRewards_used() != 0.0 && data.getStatus().equals(Status.success)) {
            Double userReferralBounus = cartService.getUserReferralBonus(cartDataEntity.getUserId());

            if (data.getRewards_used() > userReferralBounus) {
                return new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.INVALID_REFERRAL_BONUS), data.getTxn_id());
            }
            referralTransactionEntity1.setAmount(data.getRewards_used());
            referralTransactionEntity1.setTransactionType(TransactionType.debit);
            referralTransactionEntity1.setCreatedOn(TimeUtil.getCurrentUTCTime());
            referralTransactionEntity1.setUserId(cartDataEntity.getUserId());
            referralTransactionEntity1.setDescription("Rewards used");
            referralTransactionEntity1.setTxnId(data.getTxn_id());
            referralTransactionRepository.save(referralTransactionEntity1);
        } else if (data.getStatus().equals(Status.cancelled) && !conversions.isEmpty()) {
            Optional<ReferralTransactionEntity> pastEntity = referralTransactionRepository.findFirstByTxnIdOrderByIdDesc(data.getTxn_id());

            if (pastEntity.isPresent() && pastEntity.get().getTransactionType().equals(TransactionType.debit)) {
                referralTransactionEntity1.setAmount(pastEntity.get().getAmount());
                referralTransactionEntity1.setTransactionType(TransactionType.credit);
                referralTransactionEntity1.setCreatedOn(TimeUtil.getCurrentUTCTime());
                referralTransactionEntity1.setUserId(cartDataEntity.getUserId());
                referralTransactionEntity1.setTxnId(data.getTxn_id());
                referralTransactionEntity1.setDescription("Rewards returned for transaction id: " + data.getTxn_id());
                referralTransactionRepository.save(referralTransactionEntity1);
            }
        }

        ConversionDataEntity conversionDataEntity = new ConversionDataEntity(data, cartDataEntity);
        ConversionDataEntity savedConversionDataEntity = conversionDataRepository.save(conversionDataEntity);

        updateConversionDataCouponMapping(data.getStatus(), data.getTxn_id(), savedConversionDataEntity, appliedCoupons, cartDataEntity.getUserId());

        ConversionResponse response = new ConversionResponse(ConversionStatusCodes.VALUES.get(ConversionStatusCodes.SUCCESS), data.getTxn_id());

        if (!conversionDataEntityOptional.isPresent()) {
            return response;
        }

        List<ReferralBonusMappingEntity> referralBonusMappingEntities =
                referralBonusMappingRepository.findAllValidByUserType(TimeUtil.getCurrentUTCTime(), cartDataEntity.getInvoiceAmount(), ReferralUserType.referrer);
        Double tempAmount = 0.0;
        ReferralBonusMappingEntity requiredReferralBonusMappingEntity = null;

        for (ReferralBonusMappingEntity referralBonusMappingEntity : referralBonusMappingEntities) {
            if (cartDataEntity.getInvoiceAmount() > referralBonusMappingEntity.getMinCartValue()) {
                if (tempAmount <= referralBonusMappingEntity.getMaxDiscount()) {
                    tempAmount = referralBonusMappingEntity.getMaxDiscount();
                    requiredReferralBonusMappingEntity = referralBonusMappingEntity;
                }
            }
        }

        ReferralTransactionEntity referralTransactionEntity = new ReferralTransactionEntity();
        Double amount;
        String referrerCode = endUserRepository.findByUserId(cartDataEntity.getUserId()).get().getReferrerCode();

        if (referrerCode != null) {
            Optional<EndUserEntity> endUserEntity = endUserRepository.findByUserReferrerCode(referrerCode);

            if (requiredReferralBonusMappingEntity != null && endUserEntity.isPresent()) {
                if (requiredReferralBonusMappingEntity.getBonusType().equals(ReferralCouponType.percentage)) {
                    amount = Math.min(requiredReferralBonusMappingEntity.getMaxDiscount(), requiredReferralBonusMappingEntity.getBonusValue() * cartDataEntity.getInvoiceAmount());
                } else {
                    amount = requiredReferralBonusMappingEntity.getBonusValue();
                }

                referralTransactionEntity.setAmount(amount);
                referralTransactionEntity.setTransactionType(TransactionType.credit);
                referralTransactionEntity.setCreatedOn(TimeUtil.getCurrentUTCTime());
                referralTransactionEntity.setUserId(endUserEntity.get().getUserId());
                referralTransactionEntity.setDescription("Transaction Completed");
                referralTransactionRepository.save(referralTransactionEntity);
            }
        }

        return response;
    }

    private CartRequest getCartRequest (CartDataEntity cartDataEntity) {
        UserData userData = new UserData();
        userData.setUser_id(cartDataEntity.getUserId());

        List<CartItemEntity> cartItemEntities = cartItemRepository.findByCartDataEntity_id(cartDataEntity.getId());
        List<CartItem> cartItems = new ArrayList<>();

        for (CartItemEntity entity : cartItemEntities) {
            CartItem cartItem = cartItemEntityCartItemServiceMapper.mapEntityToDTO(entity, CartItem.class);

            cartItems.add(cartItem);
        }

        CartRequest cartRequest = new CartRequest();

        cartRequest.setCart_data(cartItems);
        cartRequest.setTxn_id(cartDataEntity.getTxnId());
        cartRequest.setMerchant_id(cartDataEntity.getMerchantId());
        cartRequest.setUser_data(userData);
        cartRequest.setFields(getFields());
        cartRequest.setCart_after_discount(new ArrayList<>());

        return cartRequest;
    }

    private List<String> getFields() {
        List<String> fields = new ArrayList<>();

        fields.add("coupons");
        fields.add("discounts");
        fields.add("referrals");

        return fields;
    }

    private void updateConversionDataCouponMapping(Status status, String txnId, ConversionDataEntity conversionDataEntity, List<Coupon> coupons, String userId) {
        if (status == Status.success) {
            List<ConversionDataCouponMappingEntity> conversionDataCouponMappingEntities = new ArrayList<>();

            for (Coupon coupon : coupons) {
                CouponEntity couponEntity = new CouponEntity();
                couponEntity.setId(coupon.getCouponId());

                ConversionDataCouponMappingEntity conversionDataCouponMappingEntity = new ConversionDataCouponMappingEntity(conversionDataEntity, couponEntity,
                        txnId, coupon.getAmount(), userId);

                conversionDataCouponMappingEntities.add(conversionDataCouponMappingEntity);
            }

            conversionDataCouponMappingRepository.saveAll(conversionDataCouponMappingEntities);
        } else if (status == Status.cancelled) {
            List<ConversionDataCouponMappingEntity> conversionDataCouponMappingEntities = conversionDataCouponMappingRepository.findByTxnId(txnId);

            for (ConversionDataCouponMappingEntity entity : conversionDataCouponMappingEntities) {
                entity.setActive(false);
            }

            conversionDataCouponMappingRepository.saveAll(conversionDataCouponMappingEntities);
        }
    }
}

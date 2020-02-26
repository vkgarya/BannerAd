package com.coupon.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coupon.bean.CartItem;
import com.coupon.bean.CartRequest;
import com.coupon.bean.CartResponse;
import com.coupon.bean.Coupon;
import com.coupon.bean.Referral;
import com.coupon.bean.jpa.CartDataEntity;
import com.coupon.bean.jpa.CartItemEntity;
import com.coupon.bean.jpa.ConversionDataCouponMappingEntity;
import com.coupon.bean.jpa.ConversionDataEntity;
import com.coupon.bean.jpa.CouponCodeLanguageMappingEntity;
import com.coupon.bean.jpa.CouponEntity;
import com.coupon.bean.jpa.CouponUserRestrictionsEntity;
import com.coupon.bean.jpa.EndUserEntity;
import com.coupon.bean.jpa.ReferralBonusMappingEntity;
import com.coupon.bean.jpa.ReferralTransactionEntity;
import com.coupon.bean.jpa.RuleCategoryMappingEntity;
import com.coupon.bean.jpa.RuleOfferMappingEntity;
import com.coupon.constants.CouponType;
import com.coupon.constants.ReferralUserType;
import com.coupon.constants.RestrictionType;
import com.coupon.constants.Status;
import com.coupon.constants.TransactionType;
import com.coupon.repository.CartDataRepository;
import com.coupon.repository.CartItemRepository;
import com.coupon.repository.ConversionDataCouponMappingRepository;
import com.coupon.repository.ConversionDataRepository;
import com.coupon.repository.CouponRepository;
import com.coupon.repository.CouponUserRestrictionsRepository;
import com.coupon.repository.EndUserRepository;
import com.coupon.repository.ReferralBonusMappingRepository;
import com.coupon.repository.ReferralTransactionRepository;
import com.coupon.repository.RuleCalendarMappingRepository;
import com.coupon.repository.RuleCategoryMappingRepository;
import com.coupon.repository.RuleOfferMappingRepository;
import com.coupon.service.CartService;
import com.coupon.service.CouponService;
import com.coupon.utils.TimeUtil;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private ConversionDataRepository conversionDataRepository;
    @Resource
    private CouponRepository couponRepository;
    @Resource
    private CouponService couponService;
    @Resource
    private RuleOfferMappingRepository ruleOfferMappingRepository;
    @Resource
    private RuleCalendarMappingRepository ruleCalendarMappingRepository;
    @Resource
    private RuleCategoryMappingRepository ruleCategoryMappingRepository;
    @Resource
    private ReferralBonusMappingRepository referralBonusMappingRepository;
    @Resource
    private EndUserRepository endUserRepository;
    @Resource
    private ReferralTransactionRepository referralTransactionRepository;
    @Resource
    private CartDataRepository cartDataRepository;
    @Resource
    private CartItemRepository cartItemRepository;
    @Resource
    private CouponUserRestrictionsRepository couponUserRestrictionsRepository;
    @Resource
    private ConversionDataCouponMappingRepository conversionDataCouponMappingRepository;

    @Override
    public CartResponse getCartResponse(CartRequest cartRequest) {
        CartResponse response = new CartResponse();

        if (cartRequest.hasUserId()) {
            Optional<EndUserEntity> user = endUserRepository.findByUserId(cartRequest.getUser_data().getUser_id());
            List<ConversionDataEntity> conversions = conversionDataRepository.findByTxnId(cartRequest.getTxn_id());

            if (!conversions.isEmpty()) {
                response.setTxn_id(cartRequest.getTxn_id());
                response.setStatus("error");
                response.setMessage("Duplicate txn id");
                response.setStatus_code(3003);

                return response;
            }

            if (!user.isPresent()) {
                response.setTxn_id(cartRequest.getTxn_id());
                response.setStatus("error");
                response.setMessage("Invalid user id");
                response.setStatus_code(3001);

                return response;
            }

            Optional<CartDataEntity> pastCart = cartDataRepository.findFirstByTxnIdOrderByIdDesc(cartRequest.getTxn_id());

            if (pastCart.isPresent() && !pastCart.get().getUserId().equals(cartRequest.getUser_data().getUser_id())) {
                response.setTxn_id(cartRequest.getTxn_id());
                response.setStatus("error");
                response.setMessage("Invalid txn id or user id");
                response.setStatus_code(3002);

                return response;
            }
        }

        saveCart(cartRequest);
        cartRequest.copyCartData();

        response.setCouponsAndDiscounts(getCoupons(cartRequest));
        if (cartRequest.getFields() != null && !cartRequest.getFields().isEmpty()) {
            if (!cartRequest.getFields().contains("coupons")) {
                response.setCoupons(new ArrayList<>());
            }

            if (!cartRequest.getFields().contains("discounts")) {
                response.setDiscounts(new ArrayList<>());
            }
        }

        response.setReferrals(getReferrals(cartRequest));

        if (cartRequest.hasUserId()) {
            response.setReferral_bonus(getUserReferralBonus(cartRequest.getUser_data().getUser_id()));
        }

        response.setTxn_id(cartRequest.getTxn_id());
        response.setStatus("success");
        response.setMessage("ok");
        response.setStatus_code(2000);

        return response;
    }

    @Override
    public void saveCart(CartRequest cartRequest) {
        if (cartRequest.hasUserId()) {
            CartDataEntity cartDataEntity = new CartDataEntity();

            cartDataEntity.setTxnId(cartRequest.getTxn_id());
            cartDataEntity.setMerchantId(cartRequest.getMerchant_id());
            cartDataEntity.setUserId(cartRequest.getUser_data().getUser_id());
            cartDataEntity.setInvoiceAmount(cartRequest.getTotalCartValue());
            cartDataEntity.setCreatedOn(TimeUtil.getCurrentUTCTime());

            CartDataEntity savedCartDataEntity = cartDataRepository.save(cartDataEntity);

            CartItemEntity cartItemEntity;
            List<CartItemEntity> cartItemEntities = new ArrayList<>();

            for (CartItem item : cartRequest.getCart_data()) {
                cartItemEntity = new CartItemEntity();
                cartItemEntity.setAmount(item.getAmount());
                cartItemEntity.setCategory(item.getCategory());
                cartItemEntity.setItemName(item.getItemName());
                cartItemEntity.setQuantity(item.getQuantity());
                cartItemEntity.setType(item.getType());
                cartItemEntity.setSku(item.getSku());
                cartItemEntity.setCartDataEntity(savedCartDataEntity);

                cartItemEntities.add(cartItemEntity);
            }

            cartItemRepository.saveAll(cartItemEntities);
        }
    }

    @Override
    public Double getUserReferralBonus(String userId) {
        Double amount = 0.0;
        List<ReferralTransactionEntity> transactionEntities = referralTransactionRepository.findByUserId(userId);

        for (ReferralTransactionEntity entity : transactionEntities) {
            if (entity.getTransactionType().equals(TransactionType.credit)) {
                amount += entity.getAmount();
            } else {
                amount -= entity.getAmount();
            }
        }

        return amount;
    }

    @Override
    public List<Referral> getReferrals(CartRequest cartRequest) {
        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        List<Referral> referrals = new ArrayList<>();
        if (!cartRequest.hasUserId()) {
            return referrals;
        }

        Optional<EndUserEntity> optionalEndUserEntity = endUserRepository.findByUserId(cartRequest.getUser_data().getUser_id());

        if (!optionalEndUserEntity.isPresent()) {
            return referrals;
        }

        if (optionalEndUserEntity.get().getReferrerCode() == null) {
            return referrals;
        }

        Collection<ConversionDataEntity> userConversions = couponService.getSuccessfulConversionsByUserId(cartRequest.getUser_data().getUser_id());

        if (!userConversions.isEmpty()) {
            return referrals;
        }

        List<ReferralBonusMappingEntity> activeReferralBonusList = referralBonusMappingRepository.findAllValidByUserType(now, cartRequest.getTotalCartValue(), ReferralUserType.referee);

        for (ReferralBonusMappingEntity entity : activeReferralBonusList) {
            referrals.add(new Referral(entity, cartRequest));
        }

        return referrals;
    }

    @Override
    public List<Coupon> getCoupons(CartRequest cartRequest) {
        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        Iterable<CouponEntity> allCoupons = couponRepository.findAllValid(now, cartRequest.getTotalCartValue(), 0.7, com.coupon.partner.constants.Status.ACTIVE);

        Map<Integer, CouponEntity> couponIdCouponMap = new HashMap<>();

        for (CouponEntity entity : allCoupons) {
            couponIdCouponMap.put(entity.getId(), entity);
        }

        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());
        List<Integer> invalidOfferCoupons = filterInvalidOfferCoupons(cartRequest, allCoupons);
        List<Integer> invalidCalendarCoupons = couponService.filterInvalidCalendarCoupons(couponIdCouponMap);
        List<Integer> invalidCategoryCoupons = filterInvalidCategoryCoupons(cartRequest, couponIdCouponMap);
        List<Integer> invalidUserBasedCoupons = couponService.getInvalidUserbasedCoupons(allCoupons, cartRequest.getUser_data().getUser_id());
        List<Integer> invalidTransactionBasedCoupons = couponService.getInvalidTransactionBasedCoupons(couponIdCouponMap, cartRequest.getUser_data().getUser_id());
        List<Integer> invalidEventBasedCoupons = couponService.getInvalidEventBasedCoupons(couponIdCouponMap, cartRequest.getUser_data().getUser_id());
        List<Integer> invalidReferrerCoupons = couponService.getInvalidReferrerCoupons(allCoupons, cartRequest.getUser_data().getUser_id());
        List<Integer> invalidPartnerCoupons = couponService.getInvalidPartnerCoupons(allCoupons, cartRequest.getMerchant_id());
        List<CouponEntity> validCoupons = new ArrayList<>();

        for (Integer couponId : couponIds) {
            if (!(invalidOfferCoupons.contains(couponId)
                    || invalidCalendarCoupons.contains(couponId)
                    || invalidCategoryCoupons.contains(couponId)
                    || invalidUserBasedCoupons.contains(couponId)
                    || invalidReferrerCoupons.contains(couponId)
                    || invalidPartnerCoupons.contains(couponId)
                    || invalidTransactionBasedCoupons.contains(couponId)
                    || invalidEventBasedCoupons.contains(couponId))
            ) {
                validCoupons.add(couponIdCouponMap.get(couponId));
            }
        }

        validCoupons = filterByUserLimit(validCoupons, cartRequest);

        List<Coupon> filteredCoupons = filterCoupons(cartRequest, validCoupons);

        updateFilteredCoupons(filteredCoupons, couponIdCouponMap, cartRequest);

        return filteredCoupons;
    }

    private List<CouponEntity> filterByUserLimit(List<CouponEntity> validCoupons, CartRequest cartRequest) {
        if (!cartRequest.hasUserId()) {
            return validCoupons;
        }

        List<CouponEntity> couponEntities = new ArrayList<>();
        List<ConversionDataEntity> validCouponConversions;
        List<Integer> conversionDataIds = new ArrayList<>();
        Map<Integer, List<Integer>> conversionDataIdCouponIdMap = new HashMap<>();
        Collection<ConversionDataEntity> successfulConversions = couponService.getSuccessfulConversionsByUserId(cartRequest.getUser_data().getUser_id());
        List<Integer> coupons;

        for (ConversionDataEntity entity : successfulConversions) {
            conversionDataIds.add(entity.getId());
        }

        List<ConversionDataCouponMappingEntity> conversionDataCouponMappingEntities = conversionDataCouponMappingRepository.findByConversionDataEntity_idIn(conversionDataIds);

        for (ConversionDataCouponMappingEntity entity : conversionDataCouponMappingEntities) {
            if (conversionDataIdCouponIdMap.get(entity.getConversionDataEntity().getId()) == null) {
                conversionDataIdCouponIdMap.put(entity.getConversionDataEntity().getId(), new ArrayList<>());
                List l = conversionDataIdCouponIdMap.get(entity.getConversionDataEntity().getId());
                l.add(entity.getCouponEntity().getId());
                conversionDataIdCouponIdMap.put(entity.getConversionDataEntity().getId(), l);
            } else {
                List l = conversionDataIdCouponIdMap.get(entity.getConversionDataEntity().getId());
                l.add(entity.getCouponEntity().getId());
                conversionDataIdCouponIdMap.put(entity.getConversionDataEntity().getId(), l);
            }
        }

        for (CouponEntity couponEntity : validCoupons) {
            validCouponConversions = new ArrayList<>();

            for (ConversionDataEntity conversionDataEntity : successfulConversions) {
                coupons = conversionDataIdCouponIdMap.get(conversionDataEntity.getId());

                if (coupons == null) {
                    coupons = new ArrayList<>();
                }

                if (conversionDataEntity.getCreatedOn().isAfter(couponEntity.getStartedOn())
                        && conversionDataEntity.getCreatedOn().isBefore(couponEntity.getClosedOn()) && coupons.size() > 0) {

                    if (coupons.contains(couponEntity.getId())) {
                        validCouponConversions.add(conversionDataEntity);
                    }
                }
            }

            if (couponEntity.getLimitPerUser() == null || couponEntity.getLimitPerUser() > validCouponConversions.size()) {
                couponEntities.add(couponEntity);
            }
        }

        couponEntities = filterByCouponLimitAndCouponAmountLimit(couponEntities, cartRequest);

        return couponEntities;
    }

    private List<CouponEntity> filterByCouponLimitAndCouponAmountLimit(List<CouponEntity> validCoupons, CartRequest cartRequest) {
        List<CouponEntity> couponEntities = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>();
        Map<Integer, Integer> couponIdAndCountMap = new HashMap<>();
        Map<Integer, Double> couponIdAndAmountMap = new HashMap<>();
        List<Integer> validCouponIds = new ArrayList<>();
        Map<Integer, Double> couponIdOfferAmountMap = new HashMap<>();

        for (CouponEntity couponEntity : validCoupons) {
            couponIds.add(couponEntity.getId());
        }

        List<ConversionDataCouponMappingEntity> conversionDataCouponMappingEntities = conversionDataCouponMappingRepository.findByCouponEntity_IdIn(couponIds);

        for (ConversionDataCouponMappingEntity entity : conversionDataCouponMappingEntities) {
            if (entity.getActive()) {
                if (couponIdAndCountMap.get(entity.getCouponEntity().getId()) == null) {
                    couponIdAndCountMap.put(entity.getCouponEntity().getId(), 1);
                } else {
                    couponIdAndCountMap.put(entity.getCouponEntity().getId(), couponIdAndCountMap.get(entity.getCouponEntity().getId()) + 1);
                }

                if (couponIdAndAmountMap.get(entity.getCouponEntity().getId()) == null && entity.getCouponAmount() != null) {
                    couponIdAndAmountMap.put(entity.getCouponEntity().getId(), entity.getCouponAmount());
                } else {
                    couponIdAndAmountMap.put(entity.getCouponEntity().getId(), couponIdAndAmountMap.get(entity.getCouponEntity().getId()) + entity.getCouponAmount());
                }
            }
        }

        List<Coupon> filteredCoupons = filterCoupons(cartRequest, validCoupons);

        for(Coupon coupon : filteredCoupons) {
            couponIdOfferAmountMap.put(coupon.getCouponId(), coupon.getAmount());
        }

        for (CouponEntity entity : validCoupons) {
            couponIdAndCountMap.putIfAbsent(entity.getId(), 0);
            couponIdAndAmountMap.putIfAbsent(entity.getId(), 0.0);
            couponIdOfferAmountMap.putIfAbsent(entity.getId(), 0.0);
        }

        for (CouponEntity couponEntity : validCoupons) {
            if (couponEntity.getCouponLimit() == null && couponEntity.getCouponLimitAmount() == null) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            } else if (couponEntity.getCouponLimit() == null &&
                    couponEntity.getCouponLimitAmount() != null &&
                    couponEntity.getCouponLimitAmount() > (couponIdAndAmountMap.get(couponEntity.getId()) + couponIdOfferAmountMap.get(couponEntity.getId()))) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            } else if (couponEntity.getCouponLimit() != null &&
                    couponEntity.getCouponLimit() > couponIdAndCountMap.get(couponEntity.getId()) &&
                    couponEntity.getCouponLimitAmount() == null) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            } else if (couponEntity.getCouponLimit() != null &&
                    couponEntity.getCouponLimitAmount() != null &&
                    couponEntity.getCouponLimit() > couponIdAndCountMap.get(couponEntity.getId()) &&
                    couponEntity.getCouponLimitAmount() > (couponIdAndAmountMap.get(couponEntity.getId()) + couponIdOfferAmountMap.get(couponEntity.getId()))) {
                if (validCouponIds.indexOf(couponEntity.getId()) == -1) {
                    couponEntities.add(couponEntity);
                    validCouponIds.add(couponEntity.getId());
                }
            }
        }

        return couponEntities;
    }

    private List<String> sortSkus(CartRequest cartRequest, List<String> skus) {
        return cartRequest.sortSkusByPrice(skus);
    }
    private void updateFilteredCoupons(List<Coupon> filteredCoupons, Map<Integer, CouponEntity> couponIdCouponMap, CartRequest cartRequest) {
        CouponEntity couponEntity;
        List<RuleOfferMappingEntity> ruleOfferMappingEntities;
        Map<String, Integer> cartSkus;
        Map<String, Integer> skusForOffer;
        Map<String, Integer> validOfferSkus;
        Boolean enabled;
        Integer offerProductsNeeded, index;
        String category;
        Double categoryPrice, priceDifference;
        Map<String, Double> categoryCartPriceMap;

        for (Coupon coupon : filteredCoupons) {
            couponEntity = couponIdCouponMap.get(coupon.getCouponId());
            ruleOfferMappingEntities = couponEntity.getRuleOfferMappingEntities();
            cartSkus = cartRequest.getSkuQuantityMap();
            categoryCartPriceMap = cartRequest.geCategoryCartPriceMap();

            if (couponEntity.getMinCartValue() != null && couponEntity.getMinCartValue() > cartRequest.getTotalCartValue()) {
                enabled = false;
            } else {
                enabled = true;
            }

            for (RuleOfferMappingEntity offerRule : ruleOfferMappingEntities) {
                offerProductsNeeded = checkOfferSkus(cartSkus, offerRule, cartRequest);
                String offerSkus = offerRule.getOfferSkus();
                String[] offerSkusSplit = offerSkus.split(",");
                List<String> offerSkuList = Arrays.asList(offerSkusSplit);
                validOfferSkus = new HashMap<>();

                if (offerProductsNeeded <= 0) {
                    index = 0;
                    do {
                        index += 1;
                        skusForOffer = removeBuySkus(cartSkus, sortSkus(cartRequest, new ArrayList<>(cartSkus.keySet())), offerRule);
                        cartSkus = skusForOffer;

                        for (String key : skusForOffer.keySet()) {
                            if (offerSkuList.contains(key)) {
                                validOfferSkus.put(key, skusForOffer.get(key));
                            }
                        }
                        coupon.addAmount(getBestOfferPrice(cartRequest, validOfferSkus, offerRule.getOfferQuantity(), cartSkus));
                        offerProductsNeeded = checkOfferSkus(cartSkus, offerRule, cartRequest);
                    } while (offerProductsNeeded <= 0 && validateOffer(cartSkus, offerRule) && couponEntity.getLimitPerUser() != null && index < couponEntity.getLimitPerUser());

                    coupon.setOfferCountUsed(index);
                } else {
                    enabled = false;
                    coupon.setNote("Add " + offerProductsNeeded + " or more products from the list below to avail this offer.");
                    coupon.setOffer_skus(offerRule.getOfferSkus());
                    break;
                }
            }

            if (enabled) {
                for (RuleCategoryMappingEntity categoryRule : couponEntity.getRuleCategoryMappingEntities()) {
                    category = categoryRule.getCategoryName();
                    categoryPrice = categoryCartPriceMap.get(category);

                    if (categoryPrice == null) {
                        categoryPrice = 0.0;
                    }

                    priceDifference = couponEntity.getMinCartValue() - categoryPrice;

                    if (priceDifference >= 0.0) {
                        enabled = false;
                        coupon.setNote("Add " + priceDifference + " or more worth of " + category + " to avail this offer.");
                        break;
                    }
                }
            }

            coupon.setDisabled(!enabled);

            if (enabled && couponEntity.getMinCartValue() != null && couponEntity.getMinCartValue() > cartRequest.getTotalCartValue()) {
                coupon.setDisabled(true);
            }

        }
    }

    private Double getBestOfferPrice(CartRequest cartRequest, Map<String, Integer> skusForOffer, Integer offerQuantity, Map<String, Integer> cartSkus) {
        Double amount = 0.0;
        Map<String, CartItem> cartSkusMap = cartRequest.getSkuCartItemMap();
        Integer tempQuantity = offerQuantity, tempCount;

        for (String cartSku : skusForOffer.keySet()) {
            tempCount = Math.min(tempQuantity, skusForOffer.get(cartSku));
            amount += tempCount * cartSkusMap.get(cartSku).getAmount();

            if (cartSkus.containsKey(cartSku)) {
                if (cartSkus.get(cartSku) > tempCount) {
                    cartSkus.put(cartSku, cartSkus.get(cartSku) - tempCount);
                } else {
                    cartSkus.remove(cartSku);
                }
            }

            tempQuantity -= tempCount;

            if (tempQuantity == 0) {
                break;
            }
        }

        return amount;
    }

    private List<Coupon> filterCoupons(CartRequest cartRequest, List<CouponEntity> validCoupons) {
        Map<String, List<CouponEntity>> couponCodeCouponMap = new HashMap<>();
        String couponCode;
        List<CouponEntity> couponEntities = new ArrayList<>();
        Double minCartValue;
        CouponEntity requiredCoupon;

        for (CouponEntity couponEntity : validCoupons) {
            if (!couponEntity.getListOfCouponCodes().isEmpty()) {
                couponCode = couponEntity.getListOfCouponCodes().get(0).getCouponCode();
                if (!couponCodeCouponMap.containsKey(couponCode)) {
                    couponCodeCouponMap.put(couponCode, new ArrayList<>());
                }

                couponCodeCouponMap.get(couponCode).add(couponEntity);
            }
        }

        for (String code : couponCodeCouponMap.keySet()) {
            minCartValue = 0.0;
            requiredCoupon = null;

            for (CouponEntity entity : couponCodeCouponMap.get(code)) {
                if (entity.getMinCartValue() != null && entity.getMinCartValue() > minCartValue) {
                    minCartValue = entity.getMinCartValue();
                    requiredCoupon = entity;
                }
            }

            if (requiredCoupon != null) {
                couponEntities.add(requiredCoupon);
            } else if (couponCodeCouponMap.get(code).size() == 1) {
                couponEntities.add(couponCodeCouponMap.get(code).get(0));
            }
        }

        return convertCoupons(couponEntities, cartRequest);
    }

    private List<Integer> filterInvalidCategoryCoupons(CartRequest cartRequest, Map<Integer, CouponEntity> couponIdCouponMap) {
        List<Integer> invalidCoupons = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>(couponIdCouponMap.keySet());

        Iterable<RuleCategoryMappingEntity> rules = ruleCategoryMappingRepository.findByCouponEntityIdIn(couponIds);
        Map<Integer, List<RuleCategoryMappingEntity>> couponIdRuleMap = new HashMap<>();
        Integer couponId;

        for (RuleCategoryMappingEntity rule : rules) {
            couponId = rule.getCouponEntity().getId();

            if (!couponIdRuleMap.containsKey(couponId)) {
                couponIdRuleMap.put(couponId, new ArrayList<>());
            }

            couponIdRuleMap.get(couponId).add(rule);
        }

        for (Integer coupId : couponIdRuleMap.keySet()) {
            if (!validateCategoryRule(cartRequest, couponIdRuleMap.get(coupId))) {
                invalidCoupons.add(coupId);
            }
        }

        return invalidCoupons;
    }

    private Boolean validateCategoryRule(CartRequest cartRequest, List<RuleCategoryMappingEntity> ruleCategoryMappingEntities) {
        Boolean valid = false;
        Map<String, List<CartItem>> categoryCartItemMap = cartRequest.geCategoryCartItemMap();
        Map<String, Double> categoryCartPriceMap = cartRequest.geCategoryCartPriceMap();
        CouponEntity couponEntity = ruleCategoryMappingEntities.get(0).getCouponEntity();
        Double categoryPrice;
        Integer quantity;
        String category;

        for (RuleCategoryMappingEntity rule : ruleCategoryMappingEntities) {
            category = rule.getCategoryName();
            quantity = rule.getQuantity();
            categoryPrice = categoryCartPriceMap.get(category);

            if (categoryPrice == null) {
                categoryPrice = 0.0;
            }

            valid = (quantity == null ||
                    (categoryCartItemMap.get(category) != null && categoryCartItemMap.get(category).size() >= quantity));
            valid = valid ||couponEntity.getMinCartValueToShowCoupon() < categoryPrice;

            if (valid) {
                break;
            }
        }

        return valid;
    }

    private List<Integer> filterInvalidOfferCoupons(CartRequest cartRequest, Iterable<CouponEntity> allCoupons) {
        List<Integer> invalidCoupons = new ArrayList<>();
        List<Integer> couponIds = new ArrayList<>();

        for (CouponEntity couponEntity : allCoupons) {
            if (couponEntity.getCouponType().equals(CouponType.offer)) {
                couponIds.add(couponEntity.getId());
            }
        }

        Iterable<RuleOfferMappingEntity> offers = ruleOfferMappingRepository.findByCouponEntityIdIn(couponIds);
        Map<String, Integer> cartSkus = cartRequest.getSkuQuantityMap();

        for (RuleOfferMappingEntity offerRule : offers) {
            if (!validateOffer(cartSkus, offerRule)) {
                invalidCoupons.add(offerRule.getCouponEntity().getId());
            }
        }

        return invalidCoupons;
    }

    private Map<String, Integer> removeBuySkus (Map<String, Integer> cartSkus, List<String> sortedSkus, RuleOfferMappingEntity offerRule) {
        String buySkus = offerRule.getBuySkus();
        String[] buySkusSplit = buySkus.split(",");
        List<String> buySkuList = Arrays.asList(buySkusSplit);
        Integer buyCount = offerRule.getBuyQuantity(), tempCount;
        Map<String, Integer> cartSkusCopy = new HashMap<>(cartSkus);

        for (String cartSku : sortedSkus) {
            if (buySkuList.contains(cartSku)) {
                tempCount = Math.min(buyCount, cartSkusCopy.get(cartSku));

                cartSkusCopy.put(cartSku, cartSkusCopy.get(cartSku) - tempCount);
                buyCount -= tempCount;

                if (buyCount == 0) {
                    break;
                }
            }
        }

        return cartSkusCopy;
    }

    private Integer checkOfferSkus (Map<String, Integer> cartSkus, RuleOfferMappingEntity offerRule, CartRequest cartRequest) {
        String offerSkus = offerRule.getOfferSkus();
        String[] offerSkusSplit = offerSkus.split(",");
        List<String> offerSkuList = Arrays.asList(offerSkusSplit);

        Integer count = 0;

        Map<String, Integer> cartSkusCopy = removeBuySkus(cartSkus, sortSkus(cartRequest, new ArrayList<>(cartSkus.keySet())), offerRule);

        for (String cartSku : cartSkusCopy.keySet()) {
            if (offerSkuList.contains(cartSku)) {
                count += cartSkusCopy.get(cartSku);

                if (count >= offerRule.getOfferQuantity()) {
                    break;
                }
            }
        }

        return offerRule.getOfferQuantity() - count;
    }

    private Boolean validateOffer (Map<String, Integer> cartSkus, RuleOfferMappingEntity offerRule) {
        String buySkus = offerRule.getBuySkus();
        String[] buySkusSplit = buySkus.split(",");
        List<String> buySkuList = Arrays.asList(buySkusSplit);
        Integer count = 0;

        for (String cartSku : cartSkus.keySet()) {
            if (buySkuList.contains(cartSku)) {
                count += cartSkus.get(cartSku);

                if (count >= offerRule.getBuyQuantity()) {
                    break;
                }
            }
        }

        return count >= offerRule.getBuyQuantity();
    }

    private List<Coupon> convertCoupons(List<CouponEntity> entities, CartRequest cartRequest) {
        List<Coupon> coupons = new ArrayList<>();

        for (CouponEntity entity : entities) {
            coupons.add(new Coupon(entity, cartRequest));
        }

        return coupons;
    }
}

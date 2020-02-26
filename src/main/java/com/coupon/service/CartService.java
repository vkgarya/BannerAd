package com.coupon.service;

import java.util.List;

import com.coupon.bean.CartRequest;
import com.coupon.bean.CartResponse;
import com.coupon.bean.Coupon;
import com.coupon.bean.Referral;

public interface CartService {
    CartResponse getCartResponse(CartRequest cartRequest);

    void saveCart(CartRequest cartRequest);

    Double getUserReferralBonus(String userId);

    List<Referral> getReferrals(CartRequest cartRequest);

    List<Coupon> getCoupons(CartRequest cartRequest);
}

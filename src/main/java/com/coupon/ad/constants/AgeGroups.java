package com.coupon.ad.constants;

import com.coupon.ad.bean.AgeGroup;

public class AgeGroups {

    public static AgeGroup getAgeGroup(final String ageGroupName) {
        switch (ageGroupName) {
            case "below18":
                return new AgeGroup(0, 17);
            case "18to25":
                return new AgeGroup(18, 25);
            case "26to35":
                return new AgeGroup(26, 35);
            case "36to45":
                return new AgeGroup(36, 45);
            case "above46":
                return new AgeGroup(46, 200);
            case "above45":
                return new AgeGroup(46, 200);
            default:
                return null;
        }
    }
}

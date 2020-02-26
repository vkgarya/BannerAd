package com.coupon.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartRequest implements Serializable {
    private String txn_id;
    private String merchant_id;
    private List<String> fields = new ArrayList<>();
    private UserData user_data;
    private List<CartItem> cart_data = new ArrayList<>();
    private List<CartItem> cart_after_discount = new ArrayList<>();

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public UserData getUser_data() {
        return user_data;
    }

    public void setUser_data(UserData user_data) {
        this.user_data = user_data;
    }

    public List<CartItem> getCart_data() {
        return cart_data;
    }

    public void setCart_data(List<CartItem> cart_data) {
        this.cart_data = cart_data;
    }

    public List<CartItem> getCart_after_discount() {
        return cart_after_discount;
    }

    public void setCart_after_discount(List<CartItem> cart_after_discount) {
        this.cart_after_discount = cart_after_discount;
    }

    public void copyCartData() {
        for (CartItem item : cart_data) {
            this.cart_after_discount.add(item.clone());
        }
    }

    public Double getTotalCartValue() {
        Double value = 0.0;

        if (cart_data != null) {
            for (CartItem item : cart_data) {
                if (item.getAmount() != null && item.getQuantity() != null) {
                    value += item.getAmount() * item.getQuantity();
                }
            }
        }

        return value;
    }

    public List<String> getSkus() {
        List<String> skus = new ArrayList<>();

        for (CartItem item : cart_data) {
            if (item.getSku() != null) {
                skus.add(item.getSku());
            }
        }

        return skus;
    }

    public Map<String, Integer> getSkuQuantityMap() {
        Map<String, Integer> skus = new HashMap<>();
        String sku;

        for (CartItem item : cart_data) {
            sku = item.getSku();

            if (sku != null) {
                if (!skus.containsKey(sku)) {
                    skus.put(sku, 0);
                }

                skus.put(sku, skus.get(sku) + item.getQuantity());
            }
        }

        return skus;
    }

    public List<String> sortSkusByPrice(List<String> skus) {
        Comparator<CartItem> compareByPrice = new Comparator<CartItem>() {
            @Override
            public int compare(CartItem o1, CartItem o2) {
                return o2.getAmount().compareTo(o1.getAmount());
            }
        };

        Map<String, CartItem> skuCartItemMap = getSkuCartItemMap();

        List<CartItem> items = new ArrayList<>();
        List<String> orderedSkus = new ArrayList<>();

        for (String sku : skus) {
            items.add(skuCartItemMap.get(sku));
        }

        Collections.sort(items, compareByPrice);

        for (CartItem item : items) {
            orderedSkus.add(item.getSku());
        }

        return orderedSkus;
    }

    public Map<String, Double> geCategoryCartPriceMap() {
        Map<String, Double> priceMap = new HashMap<>();
        Double price;
        String category;

        for (CartItem item : cart_data) {
            price = item.getAmount();
            category = item.getCategory();

            if (price != null && category != null) {
                if (!priceMap.containsKey(category)) {
                    priceMap.put(category, 0.0);
                }

                priceMap.put(category, priceMap.get(category) + item.getAmount() * item.getQuantity());
            }
        }

        return priceMap;
    }

    public Map<String, CartItem> getSkuCartItemMap() {
        Map<String, CartItem> map = new HashMap<>();

        for (CartItem cartItem : cart_data) {
            map.put(cartItem.getSku(),cartItem);
        }

        return map;
    }

    public Map<String, List<CartItem>> geCategoryCartItemMap() {
        Map<String, List<CartItem>> categoryMap = new HashMap<>();
        String category;

        for (CartItem item : cart_data) {
            category = item.getCategory();

            if (category != null) {
                if (!categoryMap.containsKey(category)) {
                    categoryMap.put(category, new ArrayList<CartItem>());
                }

                categoryMap.get(category).add(item);
            }
        }

        return categoryMap;
    }

    public Map<String, Double> gePaymentTypeCartPriceMap() {
        Map<String, Double> priceMap = new HashMap<>();
        Double price;
        String type;

        for (CartItem item : cart_data) {
            price = item.getAmount();
            type = item.getType();

            if (price != null && type != null) {
                if (!priceMap.containsKey(type)) {
                    priceMap.put(type, 0.0);
                }

                priceMap.put(type, priceMap.get(type) + item.getAmount() * item.getQuantity());
            }
        }

        return priceMap;
    }

    public boolean hasUserId() {
        return this.getUser_data() != null && this.getUser_data().getUser_id() != null;
    }
}

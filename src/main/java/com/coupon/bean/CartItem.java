package com.coupon.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItem implements Serializable {
    private String sku;
    private String category;
    private String type;
    @JsonProperty("item_name")
    private String itemName;
    private Double amount;
    private Integer quantity;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        if (quantity == null) {
            return 1;
        }

        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CartItem clone() {
        CartItem item = new CartItem();

        item.setAmount(this.getAmount());
        item.setCategory(this.getCategory());
        item.setItemName(this.getItemName());
        item.setQuantity(this.getQuantity());
        item.setSku(this.getSku());
        item.setType(this.getType());

        return item;
    }
}

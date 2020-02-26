package com.coupon.bean.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coupon.bean.CartItem;
import com.coupon.bean.ConversionRequest;

@Entity
@Table(name = "cart_item")
public class CartItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "category")
    private String category;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sku")
    private String sku;

    @ManyToOne
    @JoinColumn(name = "cart_id_ref", referencedColumnName = "id")
    private CartDataEntity cartDataEntity;

    public CartItemEntity() {
        super();
    }

    public CartItemEntity(CartItem item, ConversionRequest data) {
        this.amount = item.getAmount();
        this.category = item.getCategory();
        this.itemName = item.getItemName();
        this.quantity = item.getQuantity();
        this.type = item.getType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public CartDataEntity getCartDataEntity() {
        return cartDataEntity;
    }

    public void setCartDataEntity(CartDataEntity cartDataEntity) {
        this.cartDataEntity = cartDataEntity;
    }
}

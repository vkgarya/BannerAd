package com.coupon.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coupon.bean.ConversionRequest;
import com.coupon.constants.Status;
import com.coupon.utils.TimeUtil;
import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "conversion_data")
public class ConversionDataEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "msg")
    private String msg;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "rewards_used")
    private Double rewardsUsed;

    @ManyToOne
    @JoinColumn(name = "cart_id_ref", referencedColumnName = "id")
    private CartDataEntity cartDataEntity;

    @Column(name="created_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdOn;

    public ConversionDataEntity () {
        super();
    }

    public ConversionDataEntity(ConversionRequest data, CartDataEntity cartData) {
        CartDataEntity cartDataEntity1 = new CartDataEntity();

        cartDataEntity1.setId(cartData.getId());
        this.msg = data.getMsg();
        this.status = data.getStatus();
        this.createdOn = TimeUtil.getCurrentUTCTime();
        this.cartDataEntity = cartDataEntity1;
        this.rewardsUsed = data.getRewards_used();
        this.userId = cartData.getUserId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getRewardsUsed() {
        return rewardsUsed;
    }

    public void setRewardsUsed(Double rewardsUsed) {
        this.rewardsUsed = rewardsUsed;
    }

    public CartDataEntity getCartDataEntity() {
        return cartDataEntity;
    }

    public void setCartDataEntity(CartDataEntity cartDataEntity) {
        this.cartDataEntity = cartDataEntity;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

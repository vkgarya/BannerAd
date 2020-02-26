package com.coupon.user.bean;

import java.util.ArrayList;
import java.util.List;

public class UserUpdateDTO {
    private Integer id;
    private List<Integer> roles = new ArrayList<>();
    private List<Integer> partners = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public List<Integer> getPartners() {
        return partners;
    }

    public void setPartners(List<Integer> partners) {
        this.partners = partners;
    }
}

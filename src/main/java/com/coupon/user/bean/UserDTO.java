package com.coupon.user.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String email;
    @JsonProperty("is_active")
    private Boolean isActive;
    private List<Role> roles = new ArrayList<>();
    private List<Partner> partners = new ArrayList<>();

    public UserDTO() {
        super();
    }

    public UserDTO(User user) {
        super();
        this.id = user.getId();
        this.email = user.getEmail();
        this.isActive = user.getActive();
        this.roles = user.getRoles();
        this.partners=user.getPartners();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }
}

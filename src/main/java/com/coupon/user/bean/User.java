package com.coupon.user.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY (BASED ON A SINGLE FIELD)
    //----------------------------------------------------------------------
    @NotNull
    private Integer id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    //----------------------------------------------------------------------
    @NotNull
    @Size(min = 1, max = 128)
    private String password;

    @Size(max = 255)
    private String email;

    @NotNull
    private Boolean isActive = true;

    private List<Role> roles = new ArrayList<>();
    private List<Partner> partners = new ArrayList<>();

    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(final Boolean active) {
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

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append("|");
        sb.append(password);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(isActive);
        sb.append("|");
        sb.append(roles);
        sb.append("|");
        sb.append(partners);
        return sb.toString();
    }
}

package com.coupon.user.bean;

import java.io.Serializable;

public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer userId;
    private Integer roleId;

    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(roleId);
        return sb.toString();
    }

}

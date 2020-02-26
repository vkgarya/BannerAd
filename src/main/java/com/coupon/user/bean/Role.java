package com.coupon.user.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Role implements Serializable {

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
    @Size(min = 1, max = 45)
    private String name;

    private String displayName;

    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setName(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append("|");
        sb.append(name);
        return sb.toString();
    }

}

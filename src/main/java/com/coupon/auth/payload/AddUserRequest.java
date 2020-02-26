package com.coupon.auth.payload;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AddUserRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;


    private List<Integer> role_ids;
    private boolean is_active;

    private String txn_id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getRole_ids() {
        return role_ids;
    }

    public void setRole_ids(List<Integer> role_ids) {
        this.role_ids = role_ids;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role_ids=" + role_ids +
                ", is_active=" + is_active +
                ", txn_id='" + txn_id + '\'' +
                '}';
    }
}

package com.coupon.bean;

import java.io.Serializable;

public class StatusDTO implements Serializable {
    private String status;
    private Integer status_code;
    private String description;

    public StatusDTO (Integer statusCode, String status, String description) {
        super();
        this.status = status;
        this.status_code = statusCode;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "status='" + status + '\'' +
                ", status_code=" + status_code +
                ", description='" + description + '\'' +
                '}';
    }
}

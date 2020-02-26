package com.coupon.event.bean;

import java.io.Serializable;

import com.coupon.bean.StatusDTO;

public class EventResponse implements Serializable {
    private String status;
    private Integer status_code;
    private String msg;

    public EventResponse(StatusDTO statusDTO) {
        super();
        this.status = statusDTO.getStatus();
        this.status_code =statusDTO.getStatus_code();
        this.msg = statusDTO.getDescription();
    }

    public EventResponse() {
        super();
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

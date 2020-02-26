package com.coupon.event.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EventRequest implements Serializable {
    private String user_id;
    private String event_code;
    private String txn_id;
    private List<Map<String, Object>> fields;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_code() {
        return event_code;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public void setEvent_code(String event_code) {
        this.event_code = event_code;
    }

    public List<Map<String, Object>> getFields() {
        return fields;
    }

    public void setFields(List<Map<String, Object>> fields) {
        this.fields = fields;
    }
}

package com.coupon.bean;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.coupon.constants.CalenderType;
import com.coupon.constants.Condition;
import com.coupon.constants.NumericCondition;
import com.coupon.constants.Relation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleTransactionDTO {
    @JsonProperty("cal_type")
    private CalenderType calType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonProperty("type_from")
    private ZonedDateTime typeFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonProperty("type_to")
    private ZonedDateTime typeTo;

    @JsonProperty("transaction_number")
    private String transactionNumber;

    private Condition condition;

    private Relation relation;

    private Map<String, String> fields = new HashMap<>();

    public CalenderType getCalType() {
        return calType;
    }

    public void setCalType(CalenderType calType) {
        this.calType = calType;
    }

    public ZonedDateTime getTypeFrom() {
        return typeFrom;
    }

    public void setTypeFrom(ZonedDateTime typeFrom) {
        this.typeFrom = typeFrom;
    }

    public ZonedDateTime getTypeTo() {
        return typeTo;
    }

    public void setTypeTo(ZonedDateTime typeTo) {
        this.typeTo = typeTo;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}

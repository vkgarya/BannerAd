package com.coupon.event.bean;

import java.time.ZonedDateTime;

import com.coupon.event.constants.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomEventFieldDTO {
    private Integer id;

    @JsonProperty("field_name")
    private String fieldName;

    @JsonProperty("field_description")
    private String fieldDescription;

    @JsonProperty("type")
    private EventType type;

    private Boolean mandatory;

    @JsonProperty("created_on")
    private ZonedDateTime createdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }
}

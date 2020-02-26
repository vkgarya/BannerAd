package com.coupon.event.bean;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.coupon.bean.StatusDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomEventDTO {
    private Integer id;

    @JsonProperty("event_code")
    private String eventCode;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("event_description")
    private String eventDescription;

    @JsonProperty("is_active")
    private Boolean active = true;

    @JsonProperty("created_on")
    private ZonedDateTime createdOn;

    @JsonProperty("updated_on")
    private ZonedDateTime updatedOn;

    private List<CustomEventFieldDTO> fields = new ArrayList<>();

    public CustomEventDTO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<CustomEventFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<CustomEventFieldDTO> fields) {
        this.fields = fields;
    }

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

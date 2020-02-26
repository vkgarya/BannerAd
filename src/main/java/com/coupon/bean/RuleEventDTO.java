package com.coupon.bean;

import java.util.ArrayList;
import java.util.List;

import com.coupon.constants.Relation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleEventDTO {
    @JsonProperty("event_id")
     private Integer eventId;
     private List<EventFieldDTO> fields = new ArrayList<>();
     private Relation relation;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public List<EventFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<EventFieldDTO> fields) {
        this.fields = fields;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
}

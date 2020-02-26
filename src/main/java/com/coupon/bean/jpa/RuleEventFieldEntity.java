package com.coupon.bean.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coupon.bean.EventFieldDTO;
import com.coupon.constants.Condition;
import com.coupon.event.constants.EventType;

@Entity
@Table(name = "rule_event_field")
public class RuleEventFieldEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rule_event_id_ref", referencedColumnName = "id")
    private RuleEventEntity ruleEventEntity;

    @Column(name = "key_name")
    private String key;

    @Column(name = "condition_type")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(name = "value")
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RuleEventEntity getRuleEventEntity() {
        return ruleEventEntity;
    }

    public void setRuleEventEntity(RuleEventEntity ruleEventEntity) {
        this.ruleEventEntity = ruleEventEntity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

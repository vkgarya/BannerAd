package com.coupon.bean;

import com.coupon.constants.Language;

public class LangValueDTO {
    private Language language;
    private String value;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LangValueDTO(Language language, String value) {
        this.language = language;
        this.value = value;
    }

    @Override
    public String toString() {
        return "LangValueDTO{" +
                "language=" + language +
                ", value='" + value + '\'' +
                '}';
    }
}

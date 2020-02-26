package com.coupon.utils.xlsx;

import java.util.Map;

public class XLSXCSVRecord {
    private int number;
    private Map<String, String> values;

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(final Map<String, String> values) {
        this.values = values;
    }

    public boolean isMapped(final String header) {
        if (this.values == null) {
            return false;
        }

        return this.values.containsKey(header);
    }

    public String get(final String header) {
        if (!this.isMapped(header)) {
            return null;
        }

        return this.values.get(header);
    }
}

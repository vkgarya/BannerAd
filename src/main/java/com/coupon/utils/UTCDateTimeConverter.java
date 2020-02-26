package com.coupon.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UTCDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(final ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : Timestamp.valueOf(zonedDateTime.toLocalDateTime());
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(final Timestamp timestamp) {
        LocalDateTime time;

        time = timestamp == null ? null : timestamp.toLocalDateTime();

        if (time == null) {
            return null;
        } else {
            return TimeUtil.getUTCTime(time);
        }

    }
}

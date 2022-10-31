package com.volvo.tax.converters;

import com.volvo.tax.config.AppConstants;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;

/**
 * TimePropertyConverter converts property file time string to LocalTime.
 *
 * @author Lasith Perera
 */
@Component
@ConfigurationPropertiesBinding
public class TimePropertyConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        if (Objects.isNull(source)) {
            return null;
        }

        return LocalTime.parse(source, AppConstants.TIME_FORMATTER);
    }

}
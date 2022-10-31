package com.volvo.tax.converters;


import com.volvo.tax.config.AppConstants;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DatePropertyConverter converts property file date string to LocalDate.
 *
 * @author Lasith Perera
 */
@Component
@ConfigurationPropertiesBinding
public class DatePropertyConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return LocalDate.parse(source, AppConstants.DATE_FORMATTER);
    }

}
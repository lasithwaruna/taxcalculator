package com.volvo.tax.engine.rule.impl;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.rule.AbstractRuleWithoutStatus;
import com.volvo.tax.properties.TaxRuleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * YearRule valides the year and stops the request by
 * throwwing {@link IllegalArgumentException} if the year is invalid
 *
 * @author Lasith Perera
 */
@Component
@RequiredArgsConstructor
public class YearRule extends AbstractRuleWithoutStatus {

    private final TaxRuleProperties taxFreeDatesProperties;

    @Override
    public boolean rule(LocalDateTime localDateTime, PassByRefInteger taxForTheEntry) {

        if (localDateTime.getYear() == (taxFreeDatesProperties.getValidYear())) {
            return true;
        }
        throw new IllegalArgumentException(String.format("Tax application does not support %d", localDateTime.getYear()));

    }
}

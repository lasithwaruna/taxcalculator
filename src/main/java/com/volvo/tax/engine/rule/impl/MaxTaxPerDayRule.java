package com.volvo.tax.engine.rule.impl;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.rule.AbstractRuleWIthStatus;
import com.volvo.tax.properties.TaxRuleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * MaxTaxPerDayRule check if the tax value exceeds
 * The maximum amount (60 SEK) per day for vehicle.
 *
 * @author Lasith Perera
 */
@Component
public class MaxTaxPerDayRule extends AbstractRuleWIthStatus<MaxTaxPerDayRule.MaxTaxPerDayStatus> {

    @Autowired
    private TaxRuleProperties taxRuleProperties;

    public MaxTaxPerDayRule() {
        super("MaxTaxPerDayRule");
    }

    @Override
    public boolean rule(LocalDateTime localDateTime, PassByRefInteger taxForTheEntry, MaxTaxPerDayStatus status) {

        if (Objects.isNull(status.currentDate)) {
            status.currentDate = localDateTime.toLocalDate();
            status.totalTaxPerDay = taxForTheEntry.getTax();
        } else {
            if (status.currentDate.equals(localDateTime.toLocalDate())) {
                Integer tempTotalTaxPerDay = status.totalTaxPerDay + taxForTheEntry.getTax();
                if (taxRuleProperties.getMaxTaxPerDay() < tempTotalTaxPerDay) {
                    taxForTheEntry.setTax(taxRuleProperties.getMaxTaxPerDay() - status.totalTaxPerDay);
                    status.totalTaxPerDay = taxRuleProperties.getMaxTaxPerDay();
                    return false;
                } else {
                    status.totalTaxPerDay = tempTotalTaxPerDay;
                }
            } else {
                status.currentDate = localDateTime.toLocalDate();
                status.totalTaxPerDay = taxForTheEntry.getTax();
            }
        }
        return true;
    }

    @Override
    public MaxTaxPerDayStatus getNewStatus() {
        return new MaxTaxPerDayStatus();
    }


    /**
     * MaxTaxPerDayStatus used by {@link MaxTaxPerDayRule} to track the status when calculating max
     * tax for the day
     *
     * @author Lasith Perera
     */
    static class MaxTaxPerDayStatus {
        private Integer totalTaxPerDay = 0;
        private LocalDate currentDate;

    }
}

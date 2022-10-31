package com.volvo.tax.engine.rule.impl;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.rule.AbstractRuleWithoutStatus;
import com.volvo.tax.properties.TaxRuleProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * HolidayRule uses {@link TaxRuleProperties} to check if the given entry maches following criteria
 *
 * <p>The tax is not charged on weekends (Saturdays and Sundays), public holidays,
 * days before a public holiday and during the month of July.</p>
 *
 * @author Lasith Perera
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayRule extends AbstractRuleWithoutStatus {

    private final TaxRuleProperties taxFreeDatesProperties;


    @Override
    public boolean rule(LocalDateTime entryDateTime, PassByRefInteger taxForTheEntry) {

        LocalDate entryDate = entryDateTime.toLocalDate();

        boolean isTaxFreeDate = taxFreeDatesProperties.getExemptedHolidays().stream().anyMatch(holiday -> {
            LocalDate firstTaxFreeDayBeforeTheHoliday =
                    holiday.minusDays(taxFreeDatesProperties.getExemptedNoOfDatesBeforePublicHoliday());
            return holiday.equals(entryDate) ||
                    firstTaxFreeDayBeforeTheHoliday.equals(entryDate) ||
                    (firstTaxFreeDayBeforeTheHoliday.isBefore(entryDate) && holiday.isAfter(entryDate));
        });

        if (!isTaxFreeDate) {
            isTaxFreeDate = taxFreeDatesProperties.getExemptedDaysOfWeek()
                    .stream().anyMatch(dayOfWeek -> dayOfWeek.equals(entryDate.getDayOfWeek()));
        }
        if (!isTaxFreeDate) {
            isTaxFreeDate = taxFreeDatesProperties.getExemptedMonthsOfTheYear()
                    .stream().anyMatch(month -> month.equals(entryDate.getMonth()));
        }

        if (isTaxFreeDate) taxForTheEntry.setTax(0);
        return !isTaxFreeDate;

    }
}

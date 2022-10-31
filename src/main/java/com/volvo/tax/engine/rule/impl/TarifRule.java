package com.volvo.tax.engine.rule.impl;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.rule.AbstractRuleWithoutStatus;
import com.volvo.tax.entity.Tariff;
import com.volvo.tax.properties.TaxRuleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * TarifRule calcualte the tariff based on the following tariff table.
 * <p>
 * Time	Amount
 * 06:00–06:29	SEK 8
 * 06:30–06:59	SEK 13
 * 07:00–07:59	SEK 18
 * 08:00–08:29	SEK 13
 * 08:30–14:59	SEK 8
 * 15:00–15:29	SEK 13
 * 15:30–16:59	SEK 18
 * 17:00–17:59	SEK 13
 * 18:00–18:29	SEK 8
 * 18:30–05:59	SEK 0
 *
 * @author Lasith Perera
 */
@Component
@RequiredArgsConstructor
public class TarifRule extends AbstractRuleWithoutStatus {


    private final TaxRuleProperties taxFreeDatesProperties;

    @Override
    public boolean rule(LocalDateTime entryDateTime, PassByRefInteger taxForTheEntry) {

        LocalTime entryTime = entryDateTime.toLocalTime();

        Optional<Tariff> matchedTariff = taxFreeDatesProperties.getTariff()
                .stream()
                .filter(tariff ->
                        (entryTime.isAfter(tariff.getStartTime()) && entryTime.isBefore(tariff.getEndTime())) ||
                                entryTime.equals(tariff.getStartTime()) ||
                                entryTime.equals(tariff.getEndTime())).findFirst();


        if (matchedTariff.isPresent()) {
            taxForTheEntry.setTax(matchedTariff.get().getAmount());
            return true;
        } else {
            taxForTheEntry.setTax(0);
            return false;
        }


    }
}

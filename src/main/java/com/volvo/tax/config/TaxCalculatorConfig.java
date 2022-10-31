package com.volvo.tax.config;

import com.volvo.tax.engine.core.TaxCalculator;
import com.volvo.tax.engine.rule.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TaxCalculatorConfig create the TaxCalculator bean with rules.
 *
 * @author Lasith Perera
 */
@Configuration
@AllArgsConstructor
public class TaxCalculatorConfig {

    private final HolidayRule holidayRule;
    private final SingleEntryRule singleEntryRule;
    private final TarifRule tarifRule;
    private final MaxTaxPerDayRule maxTaxPerDayRule;
    private final YearRule yearRule;

    @Bean
    public TaxCalculator taxCalculator() {
        return TaxCalculator.builder()
                .addNextRule(yearRule)    // check year and if its wrong, throws exception and stops the request
                .addNextRule(holidayRule) // if it is a holiday, it wont proceed to next step
                .addNextRule(tarifRule)   // the tariff will be calculated
                .addNextRule(singleEntryRule) // once the tariff is calculated, single entry rule applies
                .addNextRule(maxTaxPerDayRule) // max tax per day will be checked
                .build();

    }
}

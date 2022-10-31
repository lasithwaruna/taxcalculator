package com.volvo.tax.properties;


import com.volvo.tax.entity.Tariff;
import com.volvo.tax.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

/**
 * TaxRuleProperties reads the data from the property file.
 *
 * @author Lasith Perera
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "tax-rules")
public class TaxRuleProperties {

    private Set<LocalDate> exemptedHolidays;

    private Set<DayOfWeek> exemptedDaysOfWeek;

    private Set<Month> exemptedMonthsOfTheYear;

    private Integer exemptedNoOfDatesBeforePublicHoliday;

    private Set<VehicleType> exemptedVehicles;

    private Integer maxTaxPerDay;

    private List<Tariff> tariff;

    private Integer singleEntryRuleMinutes;

    private Integer validYear;

}

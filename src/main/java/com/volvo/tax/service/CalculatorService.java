package com.volvo.tax.service;

import com.volvo.tax.dto.EntryDateTimeDTO;
import com.volvo.tax.engine.core.TaxCalculator;
import com.volvo.tax.entity.VehicleType;
import com.volvo.tax.properties.TaxRuleProperties;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CalculatorService is the service class which invokes {@link TaxCalculator} to check the tax on dates.
 *
 * @author Lasith Perera
 */
@Service
@RequiredArgsConstructor
@Setter
public class CalculatorService {
    private final TaxRuleProperties taxRuleProperties;

    private final TaxCalculator taxCalculator;

    public Integer calculate(VehicleType vehicleType, List<EntryDateTimeDTO> dateTimes) {
        Integer totalTax ;
        //if the vehicle type is tax exempted, 0 tax
        if (taxRuleProperties.getExemptedVehicles().contains(vehicleType)) {
            totalTax = 0;
        } else {
            List<LocalDateTime> entryDateTimeList = dateTimes
                    .stream()
                    .sorted(Comparator.comparing(EntryDateTimeDTO::getDateTime))
                    .map(EntryDateTimeDTO::getDateTime).collect(Collectors.toList());
            totalTax = taxCalculator.calculate(entryDateTimeList);
        }
        return totalTax;
    }


}

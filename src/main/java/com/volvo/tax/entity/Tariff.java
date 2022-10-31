package com.volvo.tax.entity;

import com.volvo.tax.engine.rule.impl.TarifRule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Tariff contains the rates for different time frames and
 * used by the {@link  TarifRule}
 *
 * @author Lasith Perera
 */
@Getter
@Setter
public class Tariff {
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer amount;
}

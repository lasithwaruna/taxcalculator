package com.volvo.tax.engine.rule.impl;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.rule.AbstractRuleWIthStatus;
import com.volvo.tax.properties.TaxRuleProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SingleEntryRule validates the following criteria
 *
 * <p>A single charge rule applies in Gothenburg. Under this rule,
 * a vehicle that passes several tolling stations within 60 minutes is only taxed once.
 * The amount that must be paid is the highest one.</p>
 *
 * @author Lasith Perera
 */
@Component
public class SingleEntryRule extends AbstractRuleWIthStatus<SingleEntryRule.SingleEntryStatus> {

    @Autowired
    private TaxRuleProperties taxRuleProperties;

    public SingleEntryRule() {
        super("SingleEntryRule");
    }

    @Override
    public boolean rule(LocalDateTime entryDateTime, PassByRefInteger taxForTheEntry, SingleEntryStatus status) {

        if (Objects.isNull(status.getFirstEntryWithinTheHour())) {
            status.firstEntryWithinTheHour = entryDateTime;
            status.maxTaxWithinTheHour = taxForTheEntry.getTax();
            return true;
        } else {
            if (status.getFirstEntryWithinTheHour().plusMinutes(taxRuleProperties.getSingleEntryRuleMinutes()).isAfter(entryDateTime)) {

                if (status.maxTaxWithinTheHour < taxForTheEntry.getTax()) {
                    //The previous tax is already collected to the total tax.
                    // therefore, for this entry we shall add, only the aditional amount
                    Integer tempTax = taxForTheEntry.getTax();
                    taxForTheEntry.setTax(taxForTheEntry.getTax() - status.maxTaxWithinTheHour);
                    status.maxTaxWithinTheHour = tempTax;
                    return true;
                } else {
                    taxForTheEntry.setTax(0);
                    return false;
                }


            } else {
                //the first entry after the 60 minutes, will be assign to the status again.
                status.firstEntryWithinTheHour = entryDateTime;
                status.maxTaxWithinTheHour = taxForTheEntry.getTax();
                return true;
            }
        }
    }

    @Override
    public SingleEntryStatus getNewStatus() {
        return new SingleEntryStatus();
    }

    @Getter
    @Setter
    static class SingleEntryStatus {
        private LocalDateTime firstEntryWithinTheHour;
        private Integer maxTaxWithinTheHour;
    }
}

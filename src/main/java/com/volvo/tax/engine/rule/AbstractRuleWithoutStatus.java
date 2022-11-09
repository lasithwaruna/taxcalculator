package com.volvo.tax.engine.rule;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.core.StatusStore;
import com.volvo.tax.engine.core.TaxCalculator;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * AbstractRuleWithoutStatus is the implementation of AbstractRule which supports rules without status.
 * Rules without status does not require previous status to perform its calculations.
 * <p>
 * Used by {@link TaxCalculator}
 *
 * @author Lasith Perera
 */
public abstract non-sealed class AbstractRuleWithoutStatus extends AbstractRule {

    @Override
    public final void apply(LocalDateTime localDateTime, PassByRefInteger taxForTheEntry, StatusStore statusStore) {
        boolean chain = rule(localDateTime, taxForTheEntry);
        if (chain && !Objects.isNull(this.nextRule)) {
            this.nextRule.apply(localDateTime, taxForTheEntry, statusStore);
        }

    }
    /**
     * This is the method to be implemented by the Concrete rules without status implementations.
     *
     *
     * @param entryDateTime  the datetime tax is being calculated
     * @param taxForTheEntry the tax value for the date
     * @return true or false, if true, next rule will be called
     */
    public abstract boolean rule(LocalDateTime entryDateTime, PassByRefInteger taxForTheEntry);
}

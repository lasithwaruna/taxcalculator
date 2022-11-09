package com.volvo.tax.engine.rule;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.core.StatusStore;
import com.volvo.tax.engine.core.TaxCalculator;

import java.time.LocalDateTime;

/**
 * AbstractRule needs to be implemented by every rule
 * passed to {@link TaxCalculator}
 *
 * @author Lasith Perera
 */
public sealed abstract class AbstractRule permits AbstractRuleWithoutStatus,AbstractRuleWIthStatus{

    AbstractRule nextRule;

    public abstract void apply(LocalDateTime localDateTime, PassByRefInteger taxForTheEntry, StatusStore statusStore);

    public final void next(AbstractRule rule) {
        this.nextRule = rule;
    }

}

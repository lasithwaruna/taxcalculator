package com.volvo.tax.engine.rule;

import com.volvo.tax.engine.core.PassByRefInteger;
import com.volvo.tax.engine.core.StatusStore;
import com.volvo.tax.engine.core.TaxCalculator;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * AbstractRuleWIthStatus is the implementation of AbstractRule which supports rules with status.
 * Rules with status required to maintain status of the previous calculations.
 * <p>
 * Used by {@link TaxCalculator}
 *
 * @author Lasith Perera
 */
public abstract non-sealed class AbstractRuleWIthStatus<T> extends AbstractRule {


    /**
     * ruleName will be used to store, rule specific status in the StatusStore
     */
    private final String ruleName;



    public AbstractRuleWIthStatus(String ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public final void apply(LocalDateTime localDateTime, PassByRefInteger taxForTheEntry, StatusStore statusStore) {
        Object statusObject = statusStore.get(ruleName);
        T status ;

        if (!Objects.isNull(statusObject )) status = (T) statusObject;

        else {
            status = this.getNewStatus();
            statusStore.add(this.ruleName, status);
        }
        boolean chain = rule(localDateTime, taxForTheEntry, status);


        if (chain && !Objects.isNull(this.nextRule)) {
            this.nextRule.apply(localDateTime, taxForTheEntry, statusStore);
        }

    }

    /**
     * This is the method to be implemented by the Concrete rules with status implementations.
     *
     *
     * @param localDateTime  the datetime tax is being calculated
     * @param taxForTheEntry the tax value for the date
     * @param status  represent the previous state
     * @return true or false, if true, next rule will be called
     */
    public abstract boolean rule(LocalDateTime localDateTime, PassByRefInteger taxForTheEntry, T status);


    public abstract T getNewStatus();


}

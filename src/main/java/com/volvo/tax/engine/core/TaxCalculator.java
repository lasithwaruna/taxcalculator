package com.volvo.tax.engine.core;

import com.volvo.tax.engine.rule.AbstractRule;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * TaxCalculator is the main class withc calcualtes the tax based on the configured rules
 *
 * <p>TaxCalculator will be instantiated with a builder as we need to initiate it with the first rule </p>
 *
 *
 * @author Lasith Perera
 */
public final class TaxCalculator {

    private TaxCalculator(AbstractRule rule){
        this.rule = rule;
    }

    private final AbstractRule rule;

    /**
     *
     * @param entryTimeStamps Sorted list
     */
    public Integer calculate(List<LocalDateTime> entryTimeStamps){

        PassByRefInteger totalTax = new PassByRefInteger(0);

        StatusStore statusStore = new StatusStore();
        entryTimeStamps.forEach(entryTimeStamp -> {
                    PassByRefInteger taxForTheEntry = new PassByRefInteger(0);
                    rule.apply(entryTimeStamp, taxForTheEntry, statusStore);
                    totalTax.setTax(totalTax.getTax() + taxForTheEntry.getTax());
                }
        );

        return  totalTax.getTax();
    }

    public static TaxCalculator.Builder builder(){
        return new TaxCalculator.Builder();
    }

    /**
     * Builder is the builder class for TaxCalculator.
     *
     * <p>Multiple rules can be added and rules will be executed based on the order rules were inserted. If there
     * is no rule configured, {@link RuntimeException} will be thrown </p>
     *
     * <p>Order of the rules plays an important role. If the order is incorrect, results wont be correct </p>
     *
     * @author Lasith Perera
     */
    public static class Builder{

        private Builder(){}

        private AbstractRule firstRule;
        private AbstractRule nextRule;

        public  Builder addNextRule(AbstractRule rule){
            if(Objects.isNull(firstRule )){
                this.firstRule = rule;
                this.nextRule = rule;
            }else {
                this.nextRule.next(rule);
                this.nextRule = rule;
            }
            return this;
        }

        public TaxCalculator build(){
            if(Objects.isNull(firstRule)){
                throw new RuntimeException("First rule is required to construct TaxCalculator");
            }
            return new TaxCalculator(firstRule);
        }
    }
}

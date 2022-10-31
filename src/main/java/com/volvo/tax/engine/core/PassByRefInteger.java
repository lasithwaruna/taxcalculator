package com.volvo.tax.engine.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * PassByRefInteger is used to pass the Integer as pass by reference through rules chain.
 *
 * @author Lasith Perera
 */
@Getter
@Setter
@AllArgsConstructor
public class PassByRefInteger {
    private Integer tax;
}

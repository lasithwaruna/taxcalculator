package com.volvo.tax.entity;

/**
 * VehicleType contains the given enums. And if there is a vehicle type does not fall under
 * any vehicle type, the OtherWithoutTax or OtherWithTax  category can be used depending on the tax rule
 *
 * @author Lasith Perera
 */
public enum VehicleType {

    Emergency, Busses, Diplomat, Motorcycles, Military, Foreign, Car, Van, Truck, OtherWithTax, OtherWithoutTax

}

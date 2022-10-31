package com.volvo.tax.dto;


import com.volvo.tax.resource.CalculatorController;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CalculateTaxResponseDTO used by {@link  CalculatorController}
 * as the response body
 *
 * @author Lasith Perera
 */
@Data
@AllArgsConstructor
public class CalculateTaxResponseDTO {

    private Integer tax;

}

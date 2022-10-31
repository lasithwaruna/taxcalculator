package com.volvo.tax.dto;

import com.volvo.tax.entity.VehicleType;
import com.volvo.tax.resource.CalculatorController;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * CalculateTaxRequestDTO used by {@link  CalculatorController}
 * as the request body
 *
 * @author Lasith Perera
 */
@Data
@AllArgsConstructor
public class CalculateTaxRequestDTO {

    @NotNull(message = "Vehicle type cannot be null")
    private VehicleType vehicleType;


    @Valid
    @NotEmpty(message = "dateTimes cannot be empty")
    private List<EntryDateTimeDTO> dateTimes;


}


package com.volvo.tax.resource;

import com.volvo.tax.dto.CalculateTaxRequestDTO;
import com.volvo.tax.dto.CalculateTaxResponseDTO;
import com.volvo.tax.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * CalculatorController is the API for tax calculator
 *
 * @author Lasith Perera
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
@Validated
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Operation(summary = "Calculate tax ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tax calcualted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping(value = "/tax",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CalculateTaxResponseDTO calculateTax(
            @Valid
            @RequestBody
            @NotNull(message = "Input cannot be empty.")
            CalculateTaxRequestDTO calculateTaxRequest) {
        Integer tax = calculatorService.calculate(calculateTaxRequest.getVehicleType(), calculateTaxRequest.getDateTimes());
        return new CalculateTaxResponseDTO(tax);
    }


}

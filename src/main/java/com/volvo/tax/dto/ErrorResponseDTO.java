package com.volvo.tax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * ErrorResponseDTO used as the API error response
 *
 * @author Lasith Perera
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDTO {

    private HttpStatus status;
    private String message;

}

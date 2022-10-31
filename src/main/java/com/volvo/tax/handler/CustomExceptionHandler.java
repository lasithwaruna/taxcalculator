package com.volvo.tax.handler;

import com.volvo.tax.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * CustomExceptionHandler handles the exception and wraps the details in {@link ErrorResponseDTO}
 *
 * @author Lasith Perera
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex, final WebRequest request) {

        final ErrorResponseDTO apiError = new ErrorResponseDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        return ResponseEntity
                .status(apiError.getStatus())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {

        final ErrorResponseDTO apiError = new ErrorResponseDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        return ResponseEntity
                .status(apiError.getStatus())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body(apiError);
    }


}

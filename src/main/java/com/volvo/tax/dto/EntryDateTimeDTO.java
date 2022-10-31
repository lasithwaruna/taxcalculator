package com.volvo.tax.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.volvo.tax.config.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * EntryDateTimeDTO wraps the LocalDateTime
 *
 * @author Lasith Perera
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDateTimeDTO {

    @JsonFormat(pattern = AppConstants.DATE_TIME_PATTERN)
    @NotNull(message = "Date time cannot be null")
    private LocalDateTime dateTime;


}

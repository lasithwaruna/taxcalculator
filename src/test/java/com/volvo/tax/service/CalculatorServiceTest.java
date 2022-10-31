package com.volvo.tax.service;

import com.volvo.tax.config.AppConstants;
import com.volvo.tax.dto.EntryDateTimeDTO;
import com.volvo.tax.entity.VehicleType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CalculatorServiceTest contains all test cases for tax calcualtion logic
 *
 * @author Lasith Perera
 */
@SpringBootTest
class CalculatorServiceTest {
    @Autowired
    private CalculatorService calculatorService;

    @Test
    void testExampleDates() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 21:00:00",
                "2013-01-15 21:00:00",
                "2013-02-07 06:23:27",
                "2013-02-07 15:27:00",
                "2013-02-08 06:27:00",
                "2013-02-08 06:20:27",
                "2013-02-08 14:35:00",
                "2013-02-08 15:29:00",
                "2013-02-08 15:47:00",
                "2013-02-08 16:01:00",
                "2013-02-08 16:48:00",
                "2013-02-08 17:49:00",
                "2013-02-08 18:29:00",
                "2013-02-08 18:35:00",
                "2013-03-26 14:25:00",
                "2013-03-28 14:07:27");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 89").isEqualTo(89);
    }


    @Test
    void testReverseDateOrder() {

            List<String> dateStringArray = List.of(
        "2013-10-30 17:08:19",
        "2013-10-30 07:06:19");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
        .stream()
        .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
        .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 31").isEqualTo(31);
        }

    @Test
    void testOnHoliday() {

        List<String> dateStringArray = List.of(
                "2013-03-28 21:00:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 0").isEqualTo(0);
    }

    @Test
    void testOnTaxExeptedVehicle() {

        List<String> dateStringArray = List.of(
                "2013-01-14 07:01:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Emergency, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 0").isEqualTo(0);
    }


    @Test
    void testOnDayBeforeHoliday() {

        List<String> dateStringArray = List.of(
                "2013-03-27 21:00:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 0").isEqualTo(0);
    }

    @Test
    void testOnSunday() {

        List<String> dateStringArray = List.of(
                "2013-01-06 21:00:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 0").isEqualTo(0);
    }

    @Test
    void testOnSaturday() {

        List<String> dateStringArray = List.of(
                "2013-01-05 21:00:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);

        Assertions.assertThat(tax).as("Should have value 0").isEqualTo(0);
    }

    @Test
    void testWrongYear() {

        List<String> dateStringArray = List.of(
                "2013-01-05 21:00:00",
                "2014-01-05 21:00:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> calculatorService.calculate(VehicleType.Car, entryDateTimeList));

    }

    @Test
    void testOn60MintuesTimeGap() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 06:01:00",
                "2013-01-14 07:01:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);
        Assertions.assertThat(tax).as("Should have value 26").isEqualTo(26);
    }

    @Test
    void testTwoEntriesWithinHour_secondEntryAsTheHighestTax() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 06:00:00",
                "2013-01-14 06:30:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);
        Assertions.assertThat(tax).as("Should have value 13").isEqualTo(13);
    }


    @Test
    void testTwoEntriesWithinHour_firstEntryAsTheHighestTax() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 07:59:59", // 18
                "2013-01-14 08:00:00"  // 13
        );

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray.stream().map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER))).collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);
        Assertions.assertThat(tax).as("Should have value 18").isEqualTo(18);
    }


    @Test
    void testFourEntriesWithinTwoHours_threeEntiresInTheSameHour() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 06:00:00",
                "2013-01-14 06:30:00",
                "2013-01-14 06:59:59",
                "2013-01-14 07:00:00");

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);
        Assertions.assertThat(tax).as("Should have value 31").isEqualTo(31);
    }

    @Test
    void testMaxLimietPerDay() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 06:30:00", // 13
                "2013-01-14 07:00:00", // 18
                "2013-01-14 08:00:00", // 13
                "2013-01-14 09:00:00", // 8
                "2013-01-14 10:00:00", // 8
                "2013-01-14 11:00:00", // 8
                "2013-01-14 12:00:00", // 8
                "2013-01-14 13:00:00", // 8
                "2013-01-14 14:00:00"  // 8
        );

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);
        Assertions.assertThat(tax).as("Should have value 60").isEqualTo(60);
    }

    @Test
        //NEW
    void testMaxLimietPerDayForTwoDays() {

        List<String> dateStringArray = Arrays.asList(
                "2013-01-14 06:30:00", // 13
                "2013-01-14 07:00:00", // 18
                "2013-01-14 08:00:00", // 13
                "2013-01-14 09:00:00", // 8
                "2013-01-14 10:00:00", // 8
                "2013-01-14 11:00:00", // 8
                "2013-01-14 12:00:00", // 8
                "2013-01-14 13:00:00", // 8
                "2013-01-14 14:00:00",  // 8
                "2013-01-15 06:30:00", // 13
                "2013-01-15 07:00:00", // 18
                "2013-01-15 08:00:00", // 13
                "2013-01-15 09:00:00", // 8
                "2013-01-15 10:00:00", // 8
                "2013-01-15 11:00:00", // 8
                "2013-01-15 12:00:00", // 8
                "2013-01-15 13:00:00", // 8
                "2013-01-15 14:00:00"  // 8
        );

        List<EntryDateTimeDTO> entryDateTimeList = dateStringArray
                .stream()
                .map(sDate -> new EntryDateTimeDTO(LocalDateTime.parse(sDate, AppConstants.DATE_TIME_FORMATTER)))
                .collect(Collectors.toUnmodifiableList());

        Integer tax = calculatorService.calculate(VehicleType.Car, entryDateTimeList);
        Assertions.assertThat(tax).as("Should have value 120").isEqualTo(120);
    }

}
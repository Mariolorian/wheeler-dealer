package com.mariolorian.wheelerdealer.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.IntStream;

public class LegalAgeValidator implements ConstraintValidator<LegalAgeConstraint, String> {

    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext context) {
        byte[] peselInBytes = new byte[11];
        IntStream.range(0, 11)
                .forEachOrdered(index -> peselInBytes[index] = Byte.parseByte(pesel.substring(index, index + 1)));
        int birthYear = getBirthYear(peselInBytes);
        int birthMonth = getBirthMonth(peselInBytes);
        int birthDay = getBirthDay(peselInBytes);
        LocalDate dayOfBirth = LocalDate.of(birthYear, birthMonth, birthDay);
        return Period.between(dayOfBirth, LocalDate.now()).getYears() >= 18;
    }

    private int getBirthYear(byte[] pesel) {
        int year;
        int month;
        year = 10 * pesel[0];
        year += pesel[1];
        month = 10 * pesel[2];
        month += pesel[3];
        if (month > 0 && month < 13) {
            year += 1900;
        }
        if (month > 20 && month < 33) {
            year += 2000;
        }
        return year;
    }

    private int getBirthMonth(byte[] pesel) {
        int month;
        month = 10 * pesel[2];
        month += pesel[3];
        if (month > 80 && month < 93) {
            month -= 80;
        }
        else if (month > 20 && month < 33) {
            month -= 20;
        }
        else if (month > 40 && month < 53) {
            month -= 40;
        }
        else if (month > 60 && month < 73) {
            month -= 60;
        }
        return month;
    }

    private int getBirthDay(byte[] pesel) {
        int day;
        day = 10 * pesel[4];
        day += pesel[5];
        return day;
    }

}

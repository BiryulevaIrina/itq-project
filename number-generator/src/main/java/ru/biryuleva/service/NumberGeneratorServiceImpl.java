package ru.biryuleva.service;

import org.springframework.stereotype.Service;
import ru.biryuleva.dto.NumberDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class NumberGeneratorServiceImpl implements NumberGeneratorService {
    @Override
    public NumberDto getNumber() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
        String dateString = dtf.format(date);

        Random rand = new Random();
        int randomNumber = rand.nextInt(900000000) + 100000000;
        String randomNumberString = String.format("%09d", randomNumber);

        String finalNumber = dateString + randomNumberString;

        long number = Long.parseLong(finalNumber);

        return new NumberDto(number);
    }
}

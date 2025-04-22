package me.ehp246.test.model;

import java.time.LocalDate;

public record Layoff(String company, Integer number, Double percentage, Integer priorTotal, String location,
        LocalDate date, String position, String reason) {
}
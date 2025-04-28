package no.ntnu.rentalroulette.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRange {
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private LocalTime timeFrom;
  private LocalTime timeTo;
}

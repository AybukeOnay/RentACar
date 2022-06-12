package com.kodlamaio.rentACar.business.responses.rentals;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponse {
	
	private int id;
	private int totalDays;
	private double totalPrice;
	private LocalDate pickUpDate;
	private LocalDate returnDate;
}

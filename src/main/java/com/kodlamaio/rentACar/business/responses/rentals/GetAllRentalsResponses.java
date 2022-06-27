package com.kodlamaio.rentACar.business.responses.rentals;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRentalsResponses {

	private int id;
	private int carId;
	private int pickUpCityId;
	private int returnCityId;
	
	private double totalPrice;
	private int totalDays;
	private LocalDate pickUpDate;
	private LocalDate returnDate;
}

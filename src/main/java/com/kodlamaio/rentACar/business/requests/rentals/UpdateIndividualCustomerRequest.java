package com.kodlamaio.rentACar.business.requests.rentals;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIndividualCustomerRequest {

	private int id;
	private int carId;
	private int pickUpCityId;
	private int returnCityId;
	
	private LocalDate pickUpDate;
	private LocalDate returnDate;
}

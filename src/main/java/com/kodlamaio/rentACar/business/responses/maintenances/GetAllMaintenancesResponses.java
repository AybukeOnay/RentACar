package com.kodlamaio.rentACar.business.responses.maintenances;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllMaintenancesResponses {

	private int id;
	private LocalDate dateSent;
	private LocalDate dateReturned;
	private int carId;
}

package com.kodlamaio.rentACar.business.requests.maintenances;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaintenanceRequest {

	private LocalDate dateSent;
	private LocalDate dateReturned;
	private int carId;
}

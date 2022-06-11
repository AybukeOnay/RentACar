package com.kodlamaio.rentACar.business.requests.maintenance;

import java.time.LocalDate;
import java.util.Date;

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

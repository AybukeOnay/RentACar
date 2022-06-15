package com.kodlamaio.rentACar.business.requests.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
	
	private int id;
	private String description;
	private double dailyPrice;
	private String licensePlate;
	private int kilometer;
	private int state;
	private int brandId;
	private int colorId;
	private int minFindex;

}

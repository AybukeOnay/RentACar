package com.kodlamaio.rentACar.business.responses.additionalItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalItemResponse {

	private int id;
	private String additionalItemName;
	private double dailyPrice;
	
}

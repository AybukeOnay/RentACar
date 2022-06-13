package com.kodlamaio.rentACar.business.responses.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalResponse {
	
	private int id;
	private int rentalId;
	private int additionalItemId;
}

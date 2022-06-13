package com.kodlamaio.rentACar.business.requests.additionals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalRequest {

	private int rentalId;
	private int additionalItemId;
}

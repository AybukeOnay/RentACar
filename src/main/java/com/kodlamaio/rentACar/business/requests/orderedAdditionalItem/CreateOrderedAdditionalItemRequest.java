package com.kodlamaio.rentACar.business.requests.orderedAdditionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderedAdditionalItemRequest {

	private int additionalItemId;
	private int rentalId;
}

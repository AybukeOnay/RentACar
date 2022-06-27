package com.kodlamaio.rentACar.business.requests.orderedAdditionalItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalItemRequest {

	private int id;
	private int rentalId;
	private int additionalItemId;
}

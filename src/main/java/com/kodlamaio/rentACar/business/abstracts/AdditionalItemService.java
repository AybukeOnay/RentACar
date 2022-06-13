package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.AdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AdditionalItemService {

	Result add(CreateAdditionalItemRequest createAdditionalItemRequest);
	Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest);
	Result delete(int id);
	DataResult<List<AdditionalItemResponse>> getAll();
	DataResult<AdditionalItemResponse> finById(int id);
}

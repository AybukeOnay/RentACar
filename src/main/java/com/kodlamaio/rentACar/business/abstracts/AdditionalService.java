package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.UpdateAdditionalRequest;
import com.kodlamaio.rentACar.business.responses.additionals.AdditionalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AdditionalService {
	
	Result add(CreateAdditionalRequest createAdditionalRequest);
	Result update(UpdateAdditionalRequest updateAdditionalRequest);
	Result delete(int id);
	DataResult<List<AdditionalResponse>> findAllByRentalId(int rentalId);
	
}

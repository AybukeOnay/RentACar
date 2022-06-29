package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.corporates.CreateCorporateRequest;
import com.kodlamaio.rentACar.business.requests.corporates.DeleteCorporateRequest;
import com.kodlamaio.rentACar.business.requests.corporates.UpdateCorporateRequest;
import com.kodlamaio.rentACar.business.responses.corporates.GetAllCorporatesResponse;
import com.kodlamaio.rentACar.business.responses.corporates.GetCorporateResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;

public interface CorporateCustomerService {

	Result add(CreateCorporateRequest createCorporateRequest);
	Result delete(DeleteCorporateRequest deleteCorporateRequest);
	Result update(UpdateCorporateRequest updateCorporateRequest);
	DataResult<List<GetAllCorporatesResponse>> getAll();
	DataResult<GetCorporateResponse> getById(int id);
	DataResult<List<GetAllCorporatesResponse>> getAll(Integer pageNo, Integer pageSize);
	public CorporateCustomer getCorporateCustomerById(int id);
}

package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individuals.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.responses.individuals.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerService {

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
	DataResult<List<GetAllIndividualCustomersResponse>> getAll();
	DataResult<GetIndividualCustomerResponse> getById(int id);
	public IndividualCustomer getIndividualCustomerById(int id);
}

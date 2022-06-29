package com.kodlamaio.rentACar.business.abstracts;

import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;

public interface UserCheckService {

	boolean checkIfRealPerson(CreateIndividualCustomerRequest createIndividualCustomerRequest);
}

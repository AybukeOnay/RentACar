package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.rentals.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponses;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Rental;

public interface RentalService {

	Result addIndividualCustomerRental(CreateIndividualCustomerRequest createIndividualCustomerRequest);
	Result addCorporateCustomerRental(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	Result delete(DeleteRentalRequest deleteRentalRequest);
	Result updateIndividualCustomerRental(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
	Result updateCorporateCustomerRental(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
	DataResult<List<GetAllRentalsResponses>> getAll();
	DataResult<GetRentalResponse> getById(int id);
	Rental getRentalById(int id);
}

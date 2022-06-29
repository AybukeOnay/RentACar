package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponses;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	private RentalService rentalService;
	@Autowired
	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@PostMapping("/addinvidualcustomer")
	public Result addaddinvidualcustomer(@RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return rentalService.addIndividualCustomerRental(createIndividualCustomerRequest);
	}
	
	@PostMapping("/addcorporatecustomer")
	public Result addcorporatecustomer(@RequestBody CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		return rentalService.addCorporateCustomerRental(createCorporateCustomerRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest) {
		return rentalService.delete(deleteRentalRequest);
	}
	
	@PostMapping("/updateinvidualcustomer")
	public Result updateinvidualcustomer(@RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return rentalService.updateIndividualCustomerRental(updateIndividualCustomerRequest);
	}
	
	@PostMapping("/updatecorporatecustomer")
	public Result updatecorporatecustomer(@RequestBody UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		return rentalService.updateCorporateCustomerRental(updateCorporateCustomerRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetRentalResponse> getById(@RequestParam int id){
		return this.rentalService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllRentalsResponses>> getAll(){
		return this.rentalService.getAll();
	}
}

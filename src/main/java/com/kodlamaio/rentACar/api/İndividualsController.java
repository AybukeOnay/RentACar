package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individuals.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.responses.individuals.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individuals")
public class İndividualsController {

	private IndividualCustomerService individualCustomerService;

	public İndividualsController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return individualCustomerService.add(createIndividualCustomerRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetIndividualCustomerResponse> getById(@RequestParam int id){
		return this.individualCustomerService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll(){
		return this.individualCustomerService.getAll();
	}
	
//	@GetMapping("/getAllByPage")
//	public DataResult<List<GetAllIndividualCustomersResponse>> getAll(@RequestParam int pageNo, int pageSize){
//		return individualCustomerService.getAll(pageNo, pageSize);
//	}
}

package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.requests.corporates.CreateCorporateRequest;
import com.kodlamaio.rentACar.business.requests.corporates.DeleteCorporateRequest;
import com.kodlamaio.rentACar.business.requests.corporates.UpdateCorporateRequest;
import com.kodlamaio.rentACar.business.responses.corporates.GetAllCorporatesResponse;
import com.kodlamaio.rentACar.business.responses.corporates.GetCorporateResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporates")
public class CorporateController {

	private CorporateCustomerService corporateCustomerService;

	public CorporateController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateCorporateRequest createCorporateRequest) {
		return corporateCustomerService.add(createCorporateRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCorporateRequest deleteCorporateRequest) {
		return corporateCustomerService.delete(deleteCorporateRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateCorporateRequest updateCorporateRequest) {
		return corporateCustomerService.update(updateCorporateRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetCorporateResponse> getById(@RequestParam int id){
		return this.corporateCustomerService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllCorporatesResponse>> getAll(){
		return this.corporateCustomerService.getAll();
	}
	
	@GetMapping("/getAllByPage")
	public DataResult<List<GetAllCorporatesResponse>> getAll(@RequestParam int pageNo, int pageSize){
		return corporateCustomerService.getAll(pageNo, pageSize);
	}
	
	
	
}

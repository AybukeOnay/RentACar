package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalService;
import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.UpdateAdditionalRequest;
import com.kodlamaio.rentACar.business.responses.additionals.AdditionalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionals")
public class AdditionalsController {
	
	@Autowired
	private AdditionalService additionalService;

	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalRequest createAdditionalRequest) {
		return this.additionalService.add(createAdditionalRequest);
	}
	
	@GetMapping("/getallbyrentalid/{id}")
	public DataResult<List<AdditionalResponse>> findAllByRentalId(@PathVariable int id) {
		return additionalService.findAllByRentalId(id);

	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateAdditionalRequest updateAdditionalRequest) {
		return this.additionalService.update(updateAdditionalRequest);
	}
	
	@DeleteMapping("{id}")
	public Result delete(int id) {
		return this.additionalService.delete(id);
	}
}

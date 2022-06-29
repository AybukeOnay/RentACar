package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CityService;
import com.kodlamaio.rentACar.business.requests.cities.CreateCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.DeleteCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.UpdateCityRequest;
import com.kodlamaio.rentACar.business.responses.cities.GetAllCitiesResponses;
import com.kodlamaio.rentACar.business.responses.cities.GetCityResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cities")
public class CityController {

	private CityService cityService;
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateCityRequest createCityRequest) {
		return cityService.add(createCityRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCityRequest deleteCityRequest) {
		return cityService.delete(deleteCityRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateCityRequest updateCityRequest) {
		return cityService.update(updateCityRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetCityResponse> getById(@RequestParam int id){
		return this.cityService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllCitiesResponses>> getAll(){
		return this.cityService.getAll();
	}
	
}

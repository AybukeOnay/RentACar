package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.cities.CreateCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.DeleteCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.UpdateCityRequest;
import com.kodlamaio.rentACar.business.responses.cities.GetAllCitiesResponses;
import com.kodlamaio.rentACar.business.responses.cities.GetCityResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.City;

public interface CityService {

	Result add(CreateCityRequest createCityRequest);
	Result delete(DeleteCityRequest deleteCityRequest);
	Result update(UpdateCityRequest updateCityRequest);
	DataResult<List<GetAllCitiesResponses>> getAll();
	DataResult<GetCityResponse> getById(int id);
	City getCityById(int id);
}

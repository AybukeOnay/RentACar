package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CityService;
import com.kodlamaio.rentACar.business.requests.cities.CreateCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.DeleteCityRequest;
import com.kodlamaio.rentACar.business.requests.cities.UpdateCityRequest;
import com.kodlamaio.rentACar.business.responses.cities.GetAllCitiesResponses;
import com.kodlamaio.rentACar.business.responses.cities.GetCityResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CityRepository;
import com.kodlamaio.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService {

	private CityRepository cityRepository;
	private ModelMapperService modelMapperService;

	public CityManager(CityRepository cityRepository, ModelMapperService modelMapperService) {
		this.cityRepository = cityRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {

		checkIfCityExistName(createCityRequest.getName());
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		this.cityRepository.save(city);
		return new SuccessResult("CITY.ADDED");
	}

	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) {

		cityRepository.deleteById(deleteCityRequest.getId());
		return new SuccessResult("CITY.DELETED");
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {

		checkIfCityExistName(updateCityRequest.getName());
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		this.cityRepository.save(city);
		return new SuccessResult("CITY.UPDATED");
	}

	@Override
	public City getCityById(int id) {
		checkIfCityExistById(id);
		return cityRepository.findById(id);
	}

	@Override
	public DataResult<List<GetAllCitiesResponses>> getAll() {

		List<City> cities = this.cityRepository.findAll();
		List<GetAllCitiesResponses> response = cities.stream()
				.map(city -> this.modelMapperService.forResponse().map(city, GetAllCitiesResponses.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCitiesResponses>>(response);
	}

	@Override
	public DataResult<GetCityResponse> getById(int id) {

		City city = this.cityRepository.findById(id);
		GetCityResponse response = this.modelMapperService.forResponse().map(city, GetCityResponse.class);
		return new SuccessDataResult<GetCityResponse>(response);
	}

	private void checkIfCityExistName(String name) {

		City currentCity = this.cityRepository.findByName(name);

		if (currentCity != null) {
			throw new BusinessException("CITY.EXIST");
		}
	}

	private void checkIfCityExistById(int id) {
		City city = cityRepository.findById(id);
		if (city != null)

			throw new BusinessException("CITY.EXIST");

	}
}

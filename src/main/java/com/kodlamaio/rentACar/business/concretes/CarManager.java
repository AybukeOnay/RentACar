package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.CarResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {
	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ColorService colorService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ModelMapperService modelMapperService;

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setState(1);
		if (maxBrand(createCarRequest.getBrandId())) {
			this.carRepository.save(car);
			return new SuccessResult("CAR.ADDED");
		} else {
			return new ErrorResult("NOT.ADDED");
		}

	}

	@Override
	public List<CarResponse> getAll() {

		List<Car> cars = carRepository.findAll();
		return cars.stream().map(car -> this.modelMapperService.forResponse().map(car, CarResponse.class))
				.collect(Collectors.toList());
		// return cars.stream().map(c->new CarResponse(c)).collect(Collectors.toList());
	}

	@Override
	public void deleteById(int id) {
		carRepository.deleteById(id);

	}

	@Override
	public void update(UpdateCarRequest updateCarRequest, int id) {

		Optional<Car> currentCar = carRepository.findById(id);
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		if (currentCar.isPresent()) {
			Car foundCar = currentCar.get();
			foundCar.setDailyPrice(car.getDailyPrice());
			foundCar.setDescription(car.getDescription());
			foundCar.setKilometer(car.getKilometer());
			foundCar.setLicensePlate(car.getLicensePlate());
			foundCar.setState(car.getState());
			carRepository.save(foundCar);
		}

	}

	public Car getById(int id) {
		return carRepository.findById(id).get();
	}

	private boolean maxBrand(int brandId) {
		boolean exist = false;
		if (this.carRepository.getByBrandId(brandId).size() < 5) {
			exist = true;
		} else {
			System.out.println("CAR.EXIST");
		}
		return exist;
	}

}

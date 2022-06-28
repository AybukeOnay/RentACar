package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.FindexService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponses;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private CarRepository carRepository;
	private FindexService findexService;
	private IndividualCustomerRepository individualCustomerRepository;

	public RentalManager(RentalRepository rentalRepository, ModelMapperService modelMapperService,
			CarRepository carRepository, FindexService findexService,
			IndividualCustomerRepository individualCustomerRepository) {
		this.rentalRepository = rentalRepository;
		this.modelMapperService = modelMapperService;
		this.carRepository = carRepository;
		this.findexService = findexService;
		this.individualCustomerRepository = individualCustomerRepository;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickUpDate(), createRentalRequest.getReturnDate());
		//checkUserFindexScore(createRentalRequest);
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		
		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);
		
		Car car = this.carRepository.findById(createRentalRequest.getCarId()).get();
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());
	
		car.setState(3);
		car.setCity(rental.getReturnCityId());
		
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId()).get();
		this.rentalRepository.delete(rental);
		return new SuccessResult("RENTAL.DELETED");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		checkIfCarState(updateRentalRequest.getCarId());
		checkDateToRentACar(updateRentalRequest.getPickUpDate(), updateRentalRequest.getReturnDate());
		
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		
		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);
		
		Car car = this.carRepository.findById(updateRentalRequest.getCarId()).get();
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());
	
		rental.setPickUpCityId(car.getCity());
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public DataResult<List<GetAllRentalsResponses>> getAll() {
		List<Rental> rentals = this.rentalRepository.findAll();

		List<GetAllRentalsResponses> response = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalsResponses.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllRentalsResponses>>(response);
	}

	@Override
	public DataResult<GetRentalResponse> getById(int id) {
		Rental rental = this.rentalRepository.findById(id).get();
		GetRentalResponse response = this.modelMapperService.forResponse().map(rental, GetRentalResponse.class);
		return new SuccessDataResult<GetRentalResponse>(response);
	}

	private void checkIfCarState(int id) {
		Car car = this.carRepository.findById(id).get();
		if (car.getState() == 2 || car.getState() == 3) {
			throw new BusinessException("CAR.IS.NOT.AVAIBLE");
		}
	}

	private void checkDateToRentACar(LocalDate pickupDate, LocalDate returnDate) {
		if (!pickupDate.isBefore(returnDate) || pickupDate.isBefore(LocalDate.now())) {
			throw new BusinessException("PICKUPDATE.AND.RETURNDATE.ERROR");
		}
	}

	private double isDiffReturnCityFromPickUpCity(int pickUpCity, int returnCity) {
		if (pickUpCity != returnCity) {
			return 750.0;
		}
		return 0;
	}

	private double calculateTotalPrice(Rental rental, double dailyPrice) {
		double days = rental.getTotalDays();
		double totalDailyPrice =  days * dailyPrice;
		double diffCityPrice =  isDiffReturnCityFromPickUpCity(rental.getPickUpCityId().getId(), rental.getReturnCityId().getId());
		double totalPrice = totalDailyPrice + diffCityPrice;
		return totalPrice;
	}
	
//	private void checkUserFindexScore(CreateRentalRequest createRentalRequest) {
//		Car car = this.carRepository.findById(createRentalRequest.getCarId()).get();
//		IndividualCustomer user = this.individualCustomerRepository.findById(createRentalRequest.getUserId()).get();
//		if(findeksService.checkPerson(user.getNationality()) > car.getMinFindexScore()) {
//			throw new BusinessException("USER.IS.NOT.ENOUGH.FINDEX.SCORE");
//		}
//	}

	// checkIfUserExists,chechIfCarExist
	// tarih kontrolü ve state kontrolü yapılmalıdır.
}

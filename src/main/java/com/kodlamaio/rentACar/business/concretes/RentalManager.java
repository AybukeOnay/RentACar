package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.abstracts.FindexService;
import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.requests.rentals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.rentals.GetAllRentalsResponses;
import com.kodlamaio.rentACar.business.responses.rentals.GetRentalResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private FindexService findexService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	// bir service sadece kendi repository'sini enjecte etmelidir.

	public RentalManager(RentalRepository rentalRepository, ModelMapperService modelMapperService,
			CarService carService, FindexService findexService, IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService) {
		this.rentalRepository = rentalRepository;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.findexService = findexService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public Result addIndividualCustomerRental(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		
		checkIfCarState(createIndividualCustomerRequest.getCarId());
		checkDateToRentACar(createIndividualCustomerRequest.getPickUpDate(),
				createIndividualCustomerRequest.getReturnDate());
		checkUserFindexScore(createIndividualCustomerRequest);
		Rental rental = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, Rental.class);

		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);

		Car car = this.carService.getCarById(createIndividualCustomerRequest.getCarId());
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());

		car.setState(3);
		car.setCity(rental.getReturnCityId());

		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result addCorporateCustomerRental(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		
		checkIfCarState(createCorporateCustomerRequest.getCarId());
		checkDateToRentACar(createCorporateCustomerRequest.getPickUpDate(),
				createCorporateCustomerRequest.getReturnDate());
		corporateCustomerService.getCorporateCustomerById(createCorporateCustomerRequest.getCorporateCustomerId());
		Rental rental = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, Rental.class);

		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);

		Car car = this.carService.getCarById(createCorporateCustomerRequest.getCarId());
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());

		car.setState(3);
		car.setCity(rental.getReturnCityId());

		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.ADDED");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId());
		this.rentalRepository.delete(rental);
		return new SuccessResult("RENTAL.DELETED");
	}
	
	@Override
	public Result updateIndividualCustomerRental(UpdateIndividualCustomerRequest updateRentalRequest) {
		
		checkIfCarState(updateRentalRequest.getCarId());
		checkDateToRentACar(updateRentalRequest.getPickUpDate(), updateRentalRequest.getReturnDate());

		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);

		Car car = this.carService.getCarById(updateRentalRequest.getCarId());
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());

		rental.setPickUpCityId(car.getCity());
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.UPDATED");
	}

	@Override
	public Result updateCorporateCustomerRental(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		
		checkIfCarState(updateCorporateCustomerRequest.getCarId());
		checkDateToRentACar(updateCorporateCustomerRequest.getPickUpDate(), updateCorporateCustomerRequest.getReturnDate());

		Rental rental = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, Rental.class);

		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);

		Car car = this.carService.getCarById(updateCorporateCustomerRequest.getCarId());
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());

		rental.setPickUpCityId(car.getCity());
		rental.setTotalPrice(totalPrice);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL.UPDATED");
	}
	
	@Override
	public Rental getRentalById(int id) {
		checkIfRentalExistById(id);
		return rentalRepository.findById(id);
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
		Rental rental = this.rentalRepository.findById(id);
		GetRentalResponse response = this.modelMapperService.forResponse().map(rental, GetRentalResponse.class);
		return new SuccessDataResult<GetRentalResponse>(response);
	}

	
	private void checkIfCarState(int id) {
		Car car = this.carService.getCarById(id);
		if (car.getState() == 2 || car.getState() == 3) {
			throw new BusinessException("CAR.IS.NOT.AVAILABLE");
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
		double totalDailyPrice = days * dailyPrice;
		double diffCityPrice = isDiffReturnCityFromPickUpCity(rental.getPickUpCityId().getId(),
				rental.getReturnCityId().getId());
		double totalPrice = totalDailyPrice + diffCityPrice;
		return totalPrice;
	}

	private void checkUserFindexScore(CreateIndividualCustomerRequest createRentalRequest) {
		Car car = this.carService.getCarById(createRentalRequest.getCarId());
		IndividualCustomer user = this.individualCustomerService
				.getIndividualCustomerById(createRentalRequest.getIndividualCustomerId());
		if (findexService.findexScore(user.getNationality()) > car.getMinFindexScore()) {
			throw new BusinessException("USER.IS.NOT.ENOUGH.FINDEX.SCORE");
		}
	}
	
	private void checkIfRentalExistById(int id) {
		Rental rental = this.rentalRepository.findById(id);
		if (rental != null)
			throw new BusinessException("RENTAL.EXIST");
	}

	
}

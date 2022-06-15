package com.kodlamaio.rentACar.business.concretes;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.abstracts.FindexService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.RentalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;
import com.kodlamaio.rentACar.entities.concretes.User;

@Service
public class RentalManager implements RentalService {

	@Autowired
	private RentalRepository rentalRepository;
	@Autowired
	private CarService carService;
	@Autowired
	private ModelMapperService modelMapperService;
	@Autowired
	private FindexService findexService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		// mapping
		// Rental rental = this.modelMapperService.forRequest().map(createRentalRequest,
		// Rental.class);

		Rental rental = new Rental();
		User user = this.userRepository.getById(createRentalRequest.getUserId());
		Car car = carService.getById(createRentalRequest.getCarId());
		car.setState(3);
		rental.setCar(car);
		rental.setPickUpDate(createRentalRequest.getPickUpDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());

		Period period = Period.between(createRentalRequest.getPickUpDate(), createRentalRequest.getReturnDate());
		int diff = Math.abs(period.getDays());
		rental.setTotalDays(diff);
		rental.setTotalPrice(rental.getTotalDays() * car.getDailyPrice());
		if (checkFindexValue(user.getNationalty(), car.getMinFindex())) {
			rentalRepository.save(rental);
			return new SuccessResult("RENTAL.ADDED");
		} else {

			return new ErrorResult("NOT.ADDED");
		}

	}

	@Override
	public List<RentalResponse> getAll() {
		List<Rental> rentals = rentalRepository.findAll();
		return rentals.stream().map(rental -> this.modelMapperService.forResponse().map(rental, RentalResponse.class))
				.collect(Collectors.toList());

	}

	private boolean checkFindexValue(String nationalty, int findexScore) {

		boolean state = false;
		if (this.findexService.findexScore(nationalty) > findexScore) {
			state = true;
		}
		return state;
	}

}

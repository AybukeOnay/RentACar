package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.GetAllMaintenancesResponses;
import com.kodlamaio.rentACar.business.responses.maintenances.GetMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {

	private MaintenanceRepository maintenanceRepository;
	private ModelMapperService modelMapperService;
	private CarRepository carRepository;

	public MaintenanceManager(MaintenanceRepository maintenanceRepository, ModelMapperService modelMapperService,
			CarRepository carRepository) {
		this.maintenanceRepository = maintenanceRepository;
		this.modelMapperService = modelMapperService;
		this.carRepository = carRepository;
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {

		checkCarUnderMaintenance(createMaintenanceRequest.getCarId());
		checkDateToMaintenance(createMaintenanceRequest.getDateSent(), createMaintenanceRequest.getDateReturned());
		checkCarIdFromMaintenance(createMaintenanceRequest.getCarId());
		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);

		Car car = this.carRepository.findById(createMaintenanceRequest.getCarId()).get();
		car.setState(2);

		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.ADDED");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		
		checkIsMaintenanceExists(deleteMaintenanceRequest.getId());
		Maintenance maintenance = this.maintenanceRepository.findById(deleteMaintenanceRequest.getId()).get();
		this.maintenanceRepository.delete(maintenance);
		return new SuccessResult("MAINTENANCE.DELETED");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
		
		checkIsMaintenanceExists(updateMaintenanceRequest.getId());
		Maintenance oldMaintenance = this.maintenanceRepository.findById(updateMaintenanceRequest.getId()).get();
		Car car = this.carRepository.findById(updateMaintenanceRequest.getCarId()).get();
		car.setState(2);
		checkCarChangeInUpdate(updateMaintenanceRequest.getId(), oldMaintenance.getId());
		Maintenance maintenance = this.modelMapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.UPDATED");
	}

	@Override
	public Result updateState(int carId) {
		Car car = carRepository.findById(carId).get();

		if (car.getState() == 1) {
			car.setState(2);
		} else {
			car.setState(1);
		}

		carRepository.save(car);
		return new SuccessResult("STATE.UPDATED");
	}

	@Override
	public DataResult<List<GetAllMaintenancesResponses>> getAll() {
		List<Maintenance> maintenances = this.maintenanceRepository.findAll();

		List<GetAllMaintenancesResponses> response = maintenances.stream().map(
				maintenance -> this.modelMapperService.forResponse().map(maintenance, GetAllMaintenancesResponses.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllMaintenancesResponses>>(response);
	}

	@Override
	public DataResult<GetMaintenanceResponse> getById(int id) {
		
		checkIsMaintenanceExists(id);
		Maintenance maintenance = this.maintenanceRepository.findById(id).get();
		GetMaintenanceResponse response = this.modelMapperService.forResponse().map(maintenance,
				GetMaintenanceResponse.class);
		return new SuccessDataResult<GetMaintenanceResponse>(response);
	}
	
	private void checkCarUnderMaintenance(int carId) {
		Car car = this.carRepository.findById(carId).get();
		if (car.getState() == 2) {
			throw new BusinessException("CAR.IS.ALREADY.MAINTENANCE.NOW");
		}
	}
	
	private void checkIsMaintenanceExists(int id) {
		Maintenance maintenance = this.maintenanceRepository.findById(id).get();
		if (maintenance == null) {
			throw new BusinessException("THERE.IS.NOT.MAINTENANCE");
		}
	}
	
	private void checkDateToMaintenance(LocalDate pickupDate, LocalDate returnDate) {
		if (!pickupDate.isBefore(returnDate) || pickupDate.isBefore(LocalDate.now())) {
			throw new BusinessException("PICKUPDATE.AND.RETURNDATE.ERROR");
		}
	}
	
	private void checkCarIdFromMaintenance(int carId) {
		Car car = this.carRepository.findById(carId).get();
		if (car == null) {
			throw new BusinessException("THIS.CAR.IS.NOT.IN.CAR.REPOSITORY");
		}
	}
	
	private void checkCarChangeInUpdate(int newMaintenanceId, int oldMaintenanceId) {
		Maintenance newMaintenance = this.maintenanceRepository.findById(newMaintenanceId).get();
		Maintenance oldMaintenance = this.maintenanceRepository.findById(oldMaintenanceId).get();

		if (newMaintenance.getCar().getId() != oldMaintenance.getCar().getId()) {
			Car car = this.carRepository.findById(oldMaintenance.getCar().getId()).get();
			car.setState(1);
			this.carRepository.save(car);
		}
	}



}

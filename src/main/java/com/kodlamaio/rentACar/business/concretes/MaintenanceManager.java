package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenance.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenance.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.MaintenanceRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {

	private MaintenanceRepository maintenanceRepository;
	private CarService carService;
	LocalDate currentDate = LocalDate.now();
	private ModelMapperService modelMapperService;

	@Autowired
	public MaintenanceManager(MaintenanceRepository maintenanceRepository, CarService carService,ModelMapperService modelMapperService) {
		this.maintenanceRepository = maintenanceRepository;
		this.carService = carService;
		this.modelMapperService=modelMapperService;
	}

	// maintenance'a gelen araba gelecek
	// durumu 2, yani bakımda olacak
	// datesent güncellenecek

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest, int carId) {
		// mapping
		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		//Maintenance maintenance = new Maintenance();
		maintenance.setDateSent(currentDate);
		Car car = carService.getById(createMaintenanceRequest.getCarId());
		
		if (maintenance.getDateSent()!=null && maintenance.getDateReturned() != null){
			if (maintenance.getDateSent().compareTo(maintenance.getDateReturned()) > 0) {
				//hata mesajı üret
			}
		}
		car.setId(createMaintenanceRequest.getCarId());
		car.setState(2);
		maintenance.setCar(car);
		maintenanceRepository.save(maintenance);
		return new SuccessResult("MAINTENANCE.ADDED");

	}

	@Override
	public Result update(int id, UpdateMaintenanceRequest updateMaintenanceRequest) {
		// mapping

		/*
		 * if(checkIfMaintenanceIdExists(updateMaintenanceRequest.getId())) {
		 * Maintenance updateMaintenance=new Maintenance(); Car
		 * car=carService.getById(updateMaintenanceRequest.getCarId());
		 * updateMaintenance.setCar(car); if(car.getState()==2) car.setState(1);
		 */
		
//		if(car.getState()!=1) {
			//hata mesajı üret
//		}

		Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
		if (maintenance.isPresent()) {
			Maintenance updateMaintenance = maintenance.get();
			Car car = carService.getById(updateMaintenanceRequest.getCarId());
			if (updateMaintenance.getCar().getId() != updateMaintenanceRequest.getCarId()) {
				//updateMaintenance.setCar(get) --> İçerdeki kayıtlı aracın durumu available olarak güncellenir.
			}
			if (updateMaintenanceRequest.getDateReturned() != null) {
				if (car.getState() == 2) {
					car.setState(1);
					updateMaintenance.setCar(car);
				}
			}
			
			maintenanceRepository.save(updateMaintenance);
			
		}

		return new SuccessResult();
	}

/*	public boolean checkIfMaintenanceIdExists(int id) {
		boolean exist = true;
		if (!this.maintenanceRepository.existsById(id)) {
			exist = false;
		}
		return exist;
	}
*/
}

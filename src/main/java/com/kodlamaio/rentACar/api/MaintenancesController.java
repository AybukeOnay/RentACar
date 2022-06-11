package com.kodlamaio.rentACar.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenance.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenance.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenancesController {
	
	
	MaintenanceService maintenanceService;
	
	public MaintenancesController(MaintenanceService maintenanceService)
	{
		this.maintenanceService=maintenanceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateMaintenanceRequest createMaintenanceRequest, @RequestParam int carId)
	{
		
		maintenanceService.add(createMaintenanceRequest, carId);
		return new SuccessResult("MAINTENANCE.ADDED");
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable int id)
	{
		return new SuccessResult();
	}
	
	@PutMapping("/{id}")
	public Result update(@PathVariable int id,@RequestBody UpdateMaintenanceRequest updateMaintenanceRequest)
	{
		maintenanceService.update(id, updateMaintenanceRequest);
		return new SuccessResult();
	}
}

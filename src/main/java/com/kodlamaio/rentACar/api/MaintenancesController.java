package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.MaintenanceService;
import com.kodlamaio.rentACar.business.requests.maintenances.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.DeleteMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenances.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.business.responses.maintenances.GetAllMaintenancesResponses;
import com.kodlamaio.rentACar.business.responses.maintenances.GetMaintenanceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenancesController {
	
	private MaintenanceService maintenanceService;
	@Autowired
	public MaintenancesController(MaintenanceService maintenanceService) {
		this.maintenanceService = maintenanceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateMaintenanceRequest createMaintenanceRequest) {
		return maintenanceService.add(createMaintenanceRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteMaintenanceRequest deleteMaintenanceRequest) {
		return maintenanceService.delete(deleteMaintenanceRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateMaintenanceRequest updateMaintenanceRequest) {
		return maintenanceService.update(updateMaintenanceRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetMaintenanceResponse> getById(@RequestParam int id){
		return this.maintenanceService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllMaintenancesResponses>> getAll(){
		return this.maintenanceService.getAll();
	}
	
}

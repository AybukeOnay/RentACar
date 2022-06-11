package com.kodlamaio.rentACar.business.abstracts;

import com.kodlamaio.rentACar.business.requests.maintenance.CreateMaintenanceRequest;
import com.kodlamaio.rentACar.business.requests.maintenance.UpdateMaintenanceRequest;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface MaintenanceService {
	Result add(CreateMaintenanceRequest createMaintenanceRequest, int carId);
	Result update(int id, UpdateMaintenanceRequest updateCreateMaintenance);

	
}

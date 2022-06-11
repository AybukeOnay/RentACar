
package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.requests.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.requests.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.CarResponse;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private CarService carService;
	
	public CarsController(CarService carService)
	{
		this.carService=carService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest)
	{
		return carService.add(createCarRequest);
	}
	
	@GetMapping("/getall")
	public List<CarResponse> getAll()
	{
		return carService.getAll();
	}


	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id)
	{
		carService.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody UpdateCarRequest updateCarRequest )
	{
		carService.update(updateCarRequest, id);
	}
}

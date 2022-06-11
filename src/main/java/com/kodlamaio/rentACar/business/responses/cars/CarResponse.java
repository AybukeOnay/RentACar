package com.kodlamaio.rentACar.business.responses.cars;

import com.kodlamaio.rentACar.entities.concretes.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
	private int id;
	private String description;
	private double dailyPrice;
	private String licensePlate;
	private int kilometer;
	private int state;
	private int brandId;
	private int colorId;
	
	public CarResponse(Car entity)
	{
		this.id=entity.getId();
		this.description=entity.getDescription();
		this.dailyPrice=entity.getDailyPrice();
		this.licensePlate=entity.getLicensePlate();
		this.kilometer=entity.getKilometer();
		this.state=entity.getState();
		this.brandId=entity.getBrand().getId();
		this.colorId=entity.getColor().getId();
	}

}

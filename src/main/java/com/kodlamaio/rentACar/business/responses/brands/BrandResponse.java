package com.kodlamaio.rentACar.business.responses.brands;

import com.kodlamaio.rentACar.entities.concretes.Brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
	private int id;
	private String name;
	
	public BrandResponse(Brand entity)
	{
		this.id=entity.getId();
		this.name=entity.getName();
	}

}

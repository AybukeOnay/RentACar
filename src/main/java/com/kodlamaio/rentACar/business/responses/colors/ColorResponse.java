package com.kodlamaio.rentACar.business.responses.colors;

import com.kodlamaio.rentACar.entities.concretes.Color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorResponse {
	private int id;
	private String name;
	
	public ColorResponse(Color entity)
	{
		this.id=entity.getId();
		this.name=entity.getName();
	}


}

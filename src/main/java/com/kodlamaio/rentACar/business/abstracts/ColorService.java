package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.ColorResponse;
import com.kodlamaio.rentACar.entities.concretes.Color;

public interface ColorService {
	void add(CreateColorRequest createColorRequest);
	void deleteById(int id);
	List<ColorResponse> getAll();
	void update(UpdateColorRequest updateColorRequest, int id);
	Color getColorById(int id);
}

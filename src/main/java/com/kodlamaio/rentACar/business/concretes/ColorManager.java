package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.requests.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.requests.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.ColorResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.dataAccess.abstracts.ColorRepository;
import com.kodlamaio.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {
	private ColorRepository colorRepository;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorRepository colorRepository, ModelMapperService modelMapperService) {
		this.colorRepository = colorRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public void add(CreateColorRequest createColorRequest) {
		//mapping
		Color color=this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		//color.setName(createColorRequest.getName());
		this.colorRepository.save(color);
		
	}

	@Override
	public void deleteById(int id) {
		colorRepository.deleteById(id);
	}

	@Override
	public List<ColorResponse> getAll() {
		List<Color> colors = colorRepository.findAll();
		return colors.stream().map(color -> this.modelMapperService.forResponse().map(color, ColorResponse.class)).collect(Collectors.toList());
		//return colors.stream().map(c -> new ColorResponse(c)).collect(Collectors.toList());

	}

	@Override
	public void update(UpdateColorRequest updateColorRequest, int id) {
		// mapping
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		//color.setName(updateColorRequest.getName());

		Optional<Color> currentColor = colorRepository.findById(id); //findById --> null pointer hatasını önler.Korumalı gönderiri veriyi.
		if (currentColor.isPresent()) {
			Color foundColor = currentColor.get();
			foundColor.setName(color.getName());
			colorRepository.save(foundColor);

		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public Color getColorById(int id) {
		return colorRepository.getById(id);
	}

}

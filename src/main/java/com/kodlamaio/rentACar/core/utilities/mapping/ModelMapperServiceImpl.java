package com.kodlamaio.rentACar.core.utilities.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service //--> Servis katmanı olduğunu söyler.Git otomatik dependcy ınjection yap demek
public class ModelMapperServiceImpl implements ModelMapperService {

	private ModelMapper modelMapper;
	
	public ModelMapperServiceImpl(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	@Override
	public ModelMapper forResponse() {
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);//Gevşek --> Her şey mapplenmek zorunda değil
		return this.modelMapper;
	}

	@Override
	public ModelMapper forRequest() {
		this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);//standart--> Her şey mapplenmek zorundadır.
		return this.modelMapper;
	}

}

package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalService;
import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.UpdateAdditionalRequest;
import com.kodlamaio.rentACar.business.responses.additionals.AdditionalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.Additional;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class AdditionalManager implements AdditionalService {

	@Autowired
	AdditionalRepository additionalRepository;
	
	@Autowired
	AdditionalItemRepository additionalItemRepository;
	
	@Autowired
	RentalRepository rentalRepository;
	
	@Autowired
	ModelMapperService modelMapperService;
	
	@Override
	public Result add(CreateAdditionalRequest createAdditionalRequest) {
		
		Additional additional = this.modelMapperService.forRequest().map(createAdditionalRequest, Additional.class);
		this.additionalRepository.save(additional);
		return new SuccessResult("ADDITIONAL.ADDED");
	}

	@Override
	public Result update(UpdateAdditionalRequest updateAdditionalRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<AdditionalResponse>> findAllByRentalId(int rentalId) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalService;
import com.kodlamaio.rentACar.business.requests.additionals.CreateAdditionalRequest;
import com.kodlamaio.rentACar.business.requests.additionals.UpdateAdditionalRequest;
import com.kodlamaio.rentACar.business.responses.additionals.AdditionalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalRepository;
import com.kodlamaio.rentACar.entities.concretes.Additional;

@Service
public class AdditionalManager implements AdditionalService {

	@Autowired
	AdditionalRepository additionalRepository;

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
		Additional additional = modelMapperService.forRequest().map(updateAdditionalRequest,
				Additional.class);
		additionalRepository.save(additional);
		return new SuccessResult("ADDITIONAL.UPDATED");
	}

	@Override
	public Result delete(int id) {
		additionalRepository.deleteById(id);
		return new SuccessResult("ADDITIONAL.DELETED");
	}

	@Override
	public DataResult<List<AdditionalResponse>> findAllByRentalId(int rentalId) {
		List<Additional> additionals = additionalRepository.findAllByRentalId(rentalId);
		List<AdditionalResponse> additionalResponse=additionals.stream().map(
				additionalService->modelMapperService.forResponse().map(additionalService, AdditionalResponse.class))
				.collect(Collectors.toList());
				return new SuccessDataResult<List<AdditionalResponse>>(additionalResponse);
	}

}

package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.additionalItems.UpdateAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalItems.AdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

@Service
public class AdditionalItemManager implements AdditionalItemService {

	private AdditionalItemRepository additionalItemRepository;
	private ModelMapperService modelMapperService;

	public AdditionalItemManager(AdditionalItemRepository additionalItemRepository,
			ModelMapperService modelMapperService) {

		this.additionalItemRepository = additionalItemRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalItemRequest createAdditionalItemRequest) {
		AdditionalItem additionalItem = this.modelMapperService.forRequest().map(createAdditionalItemRequest,
				AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult();
	}

	@Override
	public Result update(UpdateAdditionalItemRequest updateAdditionalItemRequest) {
		AdditionalItem additionalItem = this.modelMapperService.forRequest().map(updateAdditionalItemRequest,
				AdditionalItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult();
	}

	@Override
	public Result delete(int id) {
		additionalItemRepository.deleteById(id);
		return new SuccessResult();
	}

	@Override
	public DataResult<List<AdditionalItemResponse>> getAll() {
		
		List<AdditionalItem> additionalItems = additionalItemRepository.findAll();
		List<AdditionalItemResponse> additionalItemResponses = additionalItems.stream().map(
				additionalItem -> modelMapperService.forResponse().map(additionalItem, AdditionalItemResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalItemResponse>>(additionalItemResponses);
	}

	@Override
	public DataResult<AdditionalItemResponse> finById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}

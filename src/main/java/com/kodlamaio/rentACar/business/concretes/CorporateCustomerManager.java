package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.requests.corporates.CreateCorporateRequest;
import com.kodlamaio.rentACar.business.requests.corporates.DeleteCorporateRequest;
import com.kodlamaio.rentACar.business.requests.corporates.UpdateCorporateRequest;
import com.kodlamaio.rentACar.business.responses.corporates.GetAllCorporatesResponse;
import com.kodlamaio.rentACar.business.responses.corporates.GetCorporateResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;

public class CorporateCustomerManager implements CorporateCustomerService{

	private CorporateCustomerRepository corporateCustomerRepository;
	private ModelMapperService modelMapperService;
	
	public CorporateCustomerManager(CorporateCustomerRepository corporateCustomerRepository,
			ModelMapperService modelMapperService) {
		this.corporateCustomerRepository = corporateCustomerRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateRequest createCorporateRequest) {
		checkCorporateExistsTaxNumber(createCorporateRequest.getTaxNumber());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateRequest,
				CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE.ADDED");
	}

	@Override
	public Result delete(DeleteCorporateRequest deleteCorporateRequest) {
		checkCorporateExists(deleteCorporateRequest.getCorporateCustomerId());
		this.corporateCustomerRepository.deleteById(deleteCorporateRequest.getCorporateCustomerId());
		return new SuccessResult("CORPORATE.DELETED");
	}

	@Override
	public Result update(UpdateCorporateRequest updateCorporateRequest) {
		checkCorporateExists(updateCorporateRequest.getCorporateCustomerId());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateRequest,
				CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE.UPDATED");
	}

	@Override
	public DataResult<List<GetAllCorporatesResponse>> getAll() {
		List<CorporateCustomer> users = this.corporateCustomerRepository.findAll();

		List<GetAllCorporatesResponse> response = users.stream().map(user -> this.modelMapperService.forResponse()
				.map(user, GetAllCorporatesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCorporatesResponse>>(response,"CORPORATE.LISTED");
	}

	@Override
	public DataResult<GetCorporateResponse> getById(int id) {
		checkCorporateExists(id);
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id).get();
		
		GetCorporateResponse response = this.modelMapperService.forResponse().map(corporateCustomer, GetCorporateResponse.class);
		
		return new SuccessDataResult<GetCorporateResponse>(response,"CORPORATE.LISTED");
	}

	@Override
	public DataResult<List<GetAllCorporatesResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<CorporateCustomer> users = this.corporateCustomerRepository.findAll(pageable).getContent();

		List<GetAllCorporatesResponse> response = users.stream()
				.map(user -> this.modelMapperService.forResponse().map(user, GetAllCorporatesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllCorporatesResponse>>(response);
	}
	
	private void checkCorporateExistsTaxNumber(String taxNumber) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findByTaxNumber(taxNumber);
		if (corporateCustomer != null) {
			throw new BusinessException("CORPORATE.ALREADY.ADDED");
		}
	}

	private void checkCorporateExists(int id) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id).get();
		if (corporateCustomer == null) {
			throw new BusinessException("CORPORATE.WAS.NOT.FOUND");
		}
	}

}

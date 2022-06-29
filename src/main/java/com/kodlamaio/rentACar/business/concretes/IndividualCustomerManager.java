package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.abstracts.UserCheckService;
import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.requests.individuals.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individuals.GetAllIndividualCustomersResponse;
import com.kodlamaio.rentACar.business.responses.individuals.GetIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerRepository individualCustomerRepository;
	private ModelMapperService modelMapperService;
	private UserCheckService userCheckService;

	public IndividualCustomerManager(IndividualCustomerRepository individualCustomerRepository,
			ModelMapperService modelMapperService, UserCheckService userCheckService) {
		this.individualCustomerRepository = individualCustomerRepository;
		this.modelMapperService = modelMapperService;
		this.userCheckService = userCheckService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		checkUserNationalityFromRepository(createIndividualCustomerRequest.getNationality());
		checkIfUserExistsByNationalityFromMernis(createIndividualCustomerRequest);
		checkUserEmail(createIndividualCustomerRequest.getEmail());
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest,
				IndividualCustomer.class);
		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("USER.ADDED");
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		
		checkIfUserExists(deleteIndividualCustomerRequest.getIndividualCustomerId());
		this.individualCustomerRepository.deleteById(deleteIndividualCustomerRequest.getIndividualCustomerId());
		return new SuccessResult("USER.DELETED");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		
		checkIfUserExists(updateIndividualCustomerRequest.getIndividualCustomerId());
		checkUserNationalityFromRepository(updateIndividualCustomerRequest.getNationality());
		checkUserUpdateEmail(updateIndividualCustomerRequest.getIndividualCustomerId(), updateIndividualCustomerRequest.getEmail());
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest,
				IndividualCustomer.class);
		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("USER.UPDATED");
	}

	@Override
	public DataResult<List<GetAllIndividualCustomersResponse>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findAll();

		List<GetAllIndividualCustomersResponse> response = individualCustomers.stream()
				.map(individualCustomer -> this.modelMapperService.forResponse().map(individualCustomer, GetAllIndividualCustomersResponse.class))
				.collect(Collectors.toList()); 
		
		return new SuccessDataResult<List<GetAllIndividualCustomersResponse>>(response);
	}

	@Override
	public DataResult<GetIndividualCustomerResponse> getById(int id) {
		
		checkIfUserExists(id);
		IndividualCustomer user = this.individualCustomerRepository.findById(id).get();

		GetIndividualCustomerResponse response = this.modelMapperService.forResponse().map(user,
				GetIndividualCustomerResponse.class);
		return new SuccessDataResult<GetIndividualCustomerResponse>(response);
	}

	@Override
	public IndividualCustomer getIndividualCustomerById(int id) {
		checkIfUserExists(id);
		return individualCustomerRepository.findById(id).get();
	}

	private void checkIfUserExistsByNationalityFromMernis(CreateIndividualCustomerRequest createIndividualRequest)
			{

		if (!userCheckService.checkIfRealPerson(createIndividualRequest)) {
			throw new BusinessException("USER.IS.NOT.EXISTS.MERNIS");
		}
	}

	private void checkUserNationalityFromRepository(String nationality) {

		IndividualCustomer user = this.individualCustomerRepository.findByNationality(nationality);

		if (user != null) {
			throw new BusinessException("USER.EXISTS.REPOSITORY");
		}
	}

	private void checkIfUserExists(int id) {

		IndividualCustomer user = this.individualCustomerRepository.findById(id).get();

		if (user == null) {
			throw new BusinessException("THERE.IS.NOT.USER");
		}
	}

	private void checkUserEmail(String email) {

		IndividualCustomer user = this.individualCustomerRepository.findByEmail(email);

		if (user != null) {
			throw new BusinessException("THIS.EMAIL.ALREADEY.EXISTS");
		}
	}

	private void checkUserUpdateEmail(int userId, String email) {

		IndividualCustomer user = this.individualCustomerRepository.findById(userId).get();

		if (user.getEmail() != email) {
			checkUserEmail(email);
		}
	}

	
}

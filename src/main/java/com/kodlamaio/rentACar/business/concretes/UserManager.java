package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.UserCheckService;
import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.requests.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.UserResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.User;

@Service
public class UserManager implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ModelMapperService modelMapperService;

	@Autowired
	UserCheckService userCheckService;

	@Override
	public Result add(CreateUserRequest createUserRequest) {
		
		checkIfUserExistByNationaltyId(createUserRequest.getNationalty());
		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);
		
		if (this.userCheckService.checkIfRealPerson(user)) {
			this.userRepository.save(user);
			return new SuccessResult("USER.ADDED");
		}else {
			return new ErrorResult("NOT.ADDED");
		}
		
	}

	@Override
	public List<UserResponse> getAll() {
		List<User> users = userRepository.findAll();
		return users.stream().map(user -> this.modelMapperService.forResponse().map(user, UserResponse.class))
				.collect(Collectors.toList());

	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) {

		User userToUpdate = modelMapperService.forRequest().map(updateUserRequest, User.class);
		userRepository.save(userToUpdate);
		return new SuccessResult();

	}

	@Override
	public Result delete(int id) {
		userRepository.deleteById(id);
		return new SuccessResult();
	}
	
	private void checkIfUserExistByNationaltyId(String nationalty) {
		User currentNationaltyId=this.userRepository.findByNationalty(nationalty);
		if (currentNationaltyId != null) {
			System.out.println("USER.EXİST");
		}
	}
}

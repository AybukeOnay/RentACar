package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.UserResponse;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface UserService {

	Result add(CreateUserRequest createUserRequest);
	List<UserResponse> getAll();
}

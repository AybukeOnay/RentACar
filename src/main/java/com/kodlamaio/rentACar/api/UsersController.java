package com.kodlamaio.rentACar.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.requests.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.requests.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.UserResponse;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	private UserService userService;
	
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateUserRequest createUserRequest) {
		userService.add(createUserRequest);
		return new SuccessResult();
	}
	
	@GetMapping("/getall")
	public List<UserResponse> getAll(){
		return userService.getAll();
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable int id) {
		return userService.delete(id);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		return userService.update(updateUserRequest);
	}

}

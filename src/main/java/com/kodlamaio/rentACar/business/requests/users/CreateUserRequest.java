package com.kodlamaio.rentACar.business.requests.users;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	
	@Size(min=2)
	private String firstName;
	
	@Size(min=2)
	private String lastName;
	
	@Size(min=11,max=11)
	private String nationalyId;
	
	private String email;
	
	private String password;
}

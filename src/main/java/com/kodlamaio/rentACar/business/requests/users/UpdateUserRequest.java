package com.kodlamaio.rentACar.business.requests.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

	private int id;
	private String firstName;
	private String lastName;
	private String nationaltyId;
	private String email;
	private String password;
}

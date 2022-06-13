package com.kodlamaio.rentACar.business.requests.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	
	@NotBlank(message = "First name is not empty") //Boş bırakılamama anatasyonu
	@Size(min=2)
	@Pattern(regexp = "(^[a-zA-Z]{2,50}$)", message = "Last name must be of characters")
	private String firstName;
	
	@Size(min=2)
	@Pattern(regexp = "(^[a-zA-Z]{2,50}$)", message = "Last Name must be of characters")
	private String lastName;
	
	@NotBlank(message = "Nationaly id is not empty")
	@Pattern(regexp = "(^[0-9]{11}$)", message = "Identity number must be 11 characters")
	@Pattern(regexp = "(^\\d*[02468]$)", message = "Identity Number must be of digits and the last character must be an even number")
	@Size(min=11,max=11)
	private String nationaltyId;
	
	@Email(message = "Email should be valid")
	private String email;
	
	private String password;
}

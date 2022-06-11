package com.kodlamaio.rentACar;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RentACarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentACarApplication.class, args);
	}

	@Bean//Uygulama başlar başlamaz içini çalıştırır.
	public ModelMapper getModelMapper() {
		return new ModelMapper(); //-->Bellekte oluşturulan mapper'i getirir.
	}
}

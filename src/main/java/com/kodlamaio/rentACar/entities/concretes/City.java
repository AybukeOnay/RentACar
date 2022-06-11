package com.kodlamaio.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "pickUpCities","returnCities"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cities")
public class City {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "cityId")
	private int cityId;
	
	@Column(name= "plateCode")
	private String plateCode;
	
	@Column(name= "cityName")
	private String cityName;
	
	@OneToMany(mappedBy = "pickUpCity")
    private List<Rental> pickUpCities;

    @OneToMany(mappedBy = "returnCity")
    private List<Rental> returnCities;

}
package com.kodlamaio.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="dailyPrice")
	private double dailyPrice;
	
	@Column(name="license_plate")
	private String licensePlate;
	
	@Column(name="kilometer")
	private int kilometer;
	
	@Column(name="state")
	private int state;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name="color_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Color color;
	
	@OneToMany(mappedBy = "car")
	private List<Maintenance> maintenances;
	
	@OneToMany(mappedBy = "car")
	private List<Rental> rentals;
	

	
	
	

}

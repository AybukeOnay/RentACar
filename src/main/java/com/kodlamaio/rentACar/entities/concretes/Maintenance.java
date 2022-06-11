package com.kodlamaio.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="maintenances")
public class Maintenance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="dateSent")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateSent;
	
	@Column(name="dateReturned")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateReturned;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	

}

package com.app.tt.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class ExchangeRate {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private double multiplier;
	private String base;
	
	public ExchangeRate(String name, String base, double multiplier) {
		this.name = name;
		this.base = base;
		this.multiplier = multiplier;
	}
}

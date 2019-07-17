package com.app.tt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor 
@Getter @Setter
public class Exchange {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@OneToOne
	private ExchangeRate exchangeRate;
	
	@Min(1)
	private double baseValue;
	
	@Min(0)
	private double exchangedValue;
	
}

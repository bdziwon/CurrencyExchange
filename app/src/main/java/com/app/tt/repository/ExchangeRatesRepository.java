package com.app.tt.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tt.model.ExchangeRate;

public interface ExchangeRatesRepository extends JpaRepository<ExchangeRate, Long> {

	ExchangeRate findByNameAndBase(String name, String base);
	
}

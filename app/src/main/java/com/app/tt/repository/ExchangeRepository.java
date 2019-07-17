package com.app.tt.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tt.model.Exchange;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
	
	
}

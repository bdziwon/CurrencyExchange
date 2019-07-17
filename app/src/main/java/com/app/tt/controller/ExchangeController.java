package com.app.tt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.tt.model.Exchange;
import com.app.tt.model.ExchangeRate;
import com.app.tt.service.ExchangeService;

@RestController
@RequestMapping("exchanges")
public class ExchangeController {

	@Autowired
	private ExchangeService exchangeService;
	
	@GetMapping("/rates")
	public List<ExchangeRate> getExchangeRates() {
		return exchangeService.getExchangeRates();
	}	
	
	@GetMapping("")
	public List<Exchange> getExchangeHistory() {
		return exchangeService.getExchangeHistory();
	}
	
	@PostMapping("")
	public Exchange exchange(String fromType, String targetType, double value) {
		return exchangeService.exchange(fromType, targetType, value);
	}
}

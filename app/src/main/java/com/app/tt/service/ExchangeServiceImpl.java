package com.app.tt.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.app.tt.model.Exchange;
import com.app.tt.model.ExchangeRate;
import com.app.tt.repository.ExchangeRatesRepository;
import com.app.tt.repository.ExchangeRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class ExchangeServiceImpl implements ExchangeService {
	
	private final RestTemplate restTemplate;
	
	@Autowired
	private ExchangeRatesRepository exchangeRatesRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@PostConstruct
	public void init() {
		exchangeRatesRepository.saveAll(fetchExchangeRates());
	}
	
	public ExchangeServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<ExchangeRate> getExchangeRates() {
		return exchangeRatesRepository.findAll();
	}

	public List<ExchangeRate> fetchExchangeRates() {
		
		exchangeRatesRepository.deleteAll();
		List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();

		exchangeRates.addAll(
				fetchFromRestApi("https://api.exchangeratesapi.io/latest"));
		exchangeRates.addAll(
				fetchFromRestApi("https://api.exchangeratesapi.io/latest?base=USD"));
		exchangeRates.addAll(
				fetchFromRestApi("https://api.exchangeratesapi.io/latest?base=PLN"));
		
		return exchangeRates;
	}
	
	private List<ExchangeRate> fetchFromRestApi(String apiLink) {
		String json = this.restTemplate.getForObject(
				apiLink, String.class);
		Gson gson = new Gson();
		JsonObject obj = gson.fromJson(json, JsonObject.class);
		JsonObject rates = (JsonObject) obj.get("rates");
		
		List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
		
		rates.entrySet().stream().forEach(
				(entry) -> {
					String name = entry.getKey();
					double multiplier = entry.getValue().getAsDouble();
					String base = obj.get("base").toString();
					base = base.substring(1, base.length() - 1);
					
					ExchangeRate rate = new ExchangeRate(name, base, multiplier);	
					exchangeRates.add(rate);
				}
		);
		
		return exchangeRates;
	}

	@Override
	public List<Exchange> getExchangeHistory() {
		return exchangeRepository.findAll();
	}

	@Override
	public Exchange exchange(@PathVariable String base, 
			@PathVariable String name, @PathVariable double value) {
		ExchangeRate exchangeRate = 
				exchangeRatesRepository.findByNameAndBase(name, base);
		
		if (exchangeRate == null) {
			System.out.println("Exchange rate " + base + " -> " + 
					name + " not found");
			return null;
		}
		
		Exchange exchange = new Exchange();
		exchange.setExchangeRate(exchangeRate);
		exchange.setBaseValue(value);
		exchange.setExchangedValue(value * exchange.getExchangeRate().getMultiplier());
		
		exchangeRepository.save(exchange);
		return exchange;
	}
}

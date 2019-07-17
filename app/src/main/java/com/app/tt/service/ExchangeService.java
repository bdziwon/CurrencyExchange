package com.app.tt.service;
import java.util.List;

import com.app.tt.model.Exchange;
import com.app.tt.model.ExchangeRate;

public interface ExchangeService {
	List<ExchangeRate> getExchangeRates();
	List<Exchange> getExchangeHistory();
	Exchange exchange(String fromType, String targetType, double value);
}

package com.revature.watercanappreservems.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.watercanappreservems.dto.ModifyReserveDto;
import com.revature.watercanappreservems.dto.ReserveDto;
import com.revature.watercanappreservems.dto.StockDto;
import com.revature.watercanappreservems.model.ReserveDetails;

@Service
public class StockService {

	@Autowired
	private RestTemplate restTemplate;

	String apiUrl = "https://watercansapp-stock-ms.herokuapp.com/";

	public List<StockDto> findAllStocks() {
		ResponseEntity<StockDto[]> getForEntity = restTemplate.getForEntity(apiUrl + "/viewStock", StockDto[].class);
		StockDto[] stockList = getForEntity.getBody();
		List<StockDto> list = new ArrayList<StockDto>();
		for (StockDto stockDTO : stockList) {
			list.add(stockDTO);
		}
		return list;
	}

	void addReservedStocks(final ReserveDto reserveDto) {
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl + "/updateReservedCans", reserveDto,
				String.class);
	}

	void addReservedOrderedStocks(final ModifyReserveDto reserve) {
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl + "/updateReservedCans", reserve,
				String.class);
	}

	void subReservedOrderedStocks(final ModifyReserveDto reserve) {
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl + "/updateReservedCans", reserve,
				String.class);
	}

	 void addCancelStock(ReserveDetails cans) {
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl + "/updateReservedCans", cans,
				String.class);
	}
}

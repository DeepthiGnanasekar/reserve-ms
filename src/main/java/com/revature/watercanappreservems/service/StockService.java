package com.revature.watercanappreservems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.watercanappreservems.dto.StockDto;

@Service
public class StockService {
	@Autowired
    private RestTemplate restTemplate;
    
    String apiUrl = "https://watercansapp-stock-ms.herokuapp.com/";
    
    
    public List<StockDto> findAllStocks(){        
       ResponseEntity<List> getForEntity = restTemplate.getForEntity(apiUrl+"/viewStock", List.class);
       List<StockDto> stockList = getForEntity.getBody();
       System.out.println(stockList);
        return stockList;
    }
    
    void  addReservedStocks(final StockDto stockDTO){        
        ResponseEntity<Void> postForEntity = restTemplate.postForEntity(apiUrl+"/viewStock",stockDTO, void.class);
        System.out.println(postForEntity);
     }
    
    void  addReservedOrderedStocks(final StockDto stockDTO){        
        ResponseEntity<Void> postForEntity = restTemplate.postForEntity(apiUrl+"/viewStock",stockDTO, void.class);
        System.out.println(postForEntity);
     }
}


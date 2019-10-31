package com.revature.watercanappreservems.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.watercanappreservems.dto.ReserveDto;
import com.revature.watercanappreservems.dto.StockDto;

@Service
public class StockService {
	
	@Autowired
    private RestTemplate restTemplate;
    
    String apiUrl = "https://watercansapp-stock-ms.herokuapp.com/";
    
    public List<StockDto> findAllStocks(){        
        ResponseEntity<StockDto[]> getForEntity = restTemplate.getForEntity(apiUrl+"/viewStock", StockDto[].class);
        StockDto[] stockList = getForEntity.getBody();
        List<StockDto> list = new ArrayList<StockDto>();
        for (StockDto stockDTO : stockList) {
             list.add(stockDTO);
         }
        System.out.println(stockList);
         return list;
     }
   
    void  addReservedStocks(final ReserveDto reserveDto){        
        System.out.println(reserveDto);
       ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl+"/updateReservedCans",reserveDto, String.class);
       System.out.println(postForEntity.getBody());
    }
    
    void  addReservedOrderedStocks(final ReserveDto reserveDto){        
        System.out.println(reserveDto);
       ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl+"/updateReservedCans",reserveDto, String.class);
       System.out.println(postForEntity.getBody());
    }
    
    void  subReservedOrderedStocks(final ReserveDto reserveDto){        
        System.out.println(reserveDto);
       ResponseEntity<String> postForEntity = restTemplate.postForEntity(apiUrl+"/updateReservedCans",reserveDto, String.class);
       System.out.println(postForEntity.getBody());
    }
}


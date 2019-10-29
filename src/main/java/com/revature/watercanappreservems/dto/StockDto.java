package com.revature.watercanappreservems.dto;
import java.time.LocalDate;
import lombok.Data;

	@Data
	public class StockDto {
	    private Integer stockId;
	    private LocalDate dateTime = LocalDate.now();
	    private Integer availableCans;
	}
	
	

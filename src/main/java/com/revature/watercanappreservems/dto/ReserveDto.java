package com.revature.watercanappreservems.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReserveDto {
	private int reserveId;
	private int userId;
	private String userName;
	private int reservedCans;
	private int reservedOrderCans;
	private String status;
	private LocalDateTime date;
}

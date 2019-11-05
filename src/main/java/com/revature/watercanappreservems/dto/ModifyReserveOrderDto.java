package com.revature.watercanappreservems.dto;

import lombok.Data;

@Data
public class ModifyReserveOrderDto {
	private int reserveId;
	private int userId;
	private String userName;
	private int reservedCans;
	private int reservedOrderCans;
}

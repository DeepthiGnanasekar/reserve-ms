package com.revature.watercanappreservems.dto;

import lombok.Data;

@Data
public class ReserveMailDto {
	private String userName;
    private int reserveId;
    private String reservedcans;
    private String email;
}

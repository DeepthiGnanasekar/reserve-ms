package com.revature.watercanappreservems.dto;

import lombok.Data;

@Data
public class MailDto {

	private String email;
	private String name;
	private int reservedCans;
	private String applicationName = "WaterCanApp";
}

package com.revature.watercanappreservems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.revature.watercanappreservems.dto.MailDto;
import com.revature.watercanappreservems.dto.ReserveMailDto;

public class MailService {
	@Autowired
	RestTemplate restTemplate;

	void sendMail(final MailDto mailDto) {
		String apiUrl = "https://charity-notification.herokuapp.com";
		ResponseEntity<Void> postForEntity = restTemplate.postForEntity(apiUrl + "/mail/registeruser", mailDto,
				Void.class);
		System.out.println(postForEntity);
	}
	
	void sendMail(final ReserveMailDto mailDto) {
		String apiUrl = "https://charity-notification.herokuapp.com";
		ResponseEntity<Void> postForEntity = restTemplate.postForEntity(apiUrl + "/waterCan/reserveCans", mailDto,
				Void.class);
		System.out.println(postForEntity);
	}
}

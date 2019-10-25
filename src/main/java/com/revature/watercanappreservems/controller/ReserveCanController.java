package com.revature.watercanappreservems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.revature.watercanappreservems.dto.Message;
import com.revature.watercanappreservems.dto.ReserveDto;
import com.revature.watercanappreservems.exception.ServiceException;
import com.revature.watercanappreservems.model.ReserveDetails;
import com.revature.watercanappreservems.service.ReserveCanService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ReserveCanController {
	
	@Autowired
	private ReserveCanService reserveCanService;

	@PostMapping("reserveCan")
	@ApiOperation("ReserveCanApi")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Reserved Success", response = Message.class),
			@ApiResponse(code = 400, message = "Order failed") })
	public ResponseEntity<Object> reserveCans(@RequestBody ReserveDto reserve) {
		String errorMessage = null;
		Message message = null;
		String status = null;
		ReserveDetails result = null;
		try {
			result = reserveCanService.reserveStock(reserve);
			status = "Success";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		if (status != null) {
			message = new Message(status);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("reserveOrderCan")
	@ApiOperation("ReserveOrderCanApi")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order Success!!", response = Message.class),
			@ApiResponse(code = 400, message = "Order Failure") })
	public ResponseEntity<?> reserveOrderCan(@RequestParam("reserveId") Integer reserveId) throws ServiceException {
		String errorMessage = null;
		ReserveDetails reserveCan = null;
		try {
			reserveCan = reserveCanService.reserveorderCan(reserveId);
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		if (reserveCan != null) {
			return new ResponseEntity<>(reserveCan, HttpStatus.OK);
		}
		Message message = new Message(errorMessage);
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("modifiedReservedCan")
	@ApiOperation("ModifiedReservedCanApi")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Order Success!!", response = Message.class),
			@ApiResponse(code = 400, message = "Order Failure") })
	public ResponseEntity<?> modifiedReservedCan(@RequestBody ReserveDto reserve) throws ServiceException {
		String errorMessage = null;
		ReserveDetails orderCan = null;
		try {
			orderCan = reserveCanService.modifiedReserveCan(reserve);
		} catch (Exception e) {
			errorMessage = e.getMessage();
		}
		if (orderCan != null) {
			return new ResponseEntity<>(orderCan, HttpStatus.OK);
		} else {
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
}

package com.revature.watercanappreservems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.revature.watercanappreservems.dto.Message;
import com.revature.watercanappreservems.dto.ModifyReserveOrderDto;
import com.revature.watercanappreservems.dto.ReserveDto;
import com.revature.watercanappreservems.dto.ReserveOrderDto;
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
	@ApiResponse(code = 400, message = "Reservation is not done") })
	public ResponseEntity<Object> reserveCan(@RequestBody ReserveDto reserve) {
		String errorMessage = null;
		Message message = null;
		ReserveDetails result = null;
		try {
			result = reserveCanService.reserveCan(reserve);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * @PostMapping("reserveCan")
	 * 
	 * @ApiOperation("ReserveCanApi")
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 200, message =
	 * "Reserved Success", response = Message.class),
	 * 
	 * @ApiResponse(code = 400, message = "Reservation is not done") }) public
	 * ResponseEntity<Object> reserveCan(@RequestBody ReserveDto reserve) { String
	 * errorMessage = null; Message message = null; String status = null;
	 * ReserveDetails result = null; try { result =
	 * reserveCanService.reserveStock(reserve); status = "Success"; }
	 * catch(ServiceException e) { errorMessage = e.getMessage(); } if (status !=
	 * null) { message = new Message(status); return new
	 * ResponseEntity<>(result,HttpStatus.CREATED); } else { message = new
	 * Message(errorMessage); return new ResponseEntity<>(message,
	 * HttpStatus.BAD_REQUEST); } }
	 */

	@PostMapping("reserveOrderCan")
	@ApiOperation("ReserveOrderCanApi")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ReservedOrdered Success!!", response = Message.class),
			@ApiResponse(code = 400, message = "ReservedOrdered Failure") })
	public ResponseEntity<?> reserveOrderCan(@RequestBody ReserveOrderDto reserve) {
		String errorMessage = null;
		ReserveDetails reserveCan = null;
		try {
			reserveCan = reserveCanService.reserveorderCan(reserve);
		} catch (ServiceException e) {
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
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ReservedModifyOrdered Success!!", response = Message.class),
			@ApiResponse(code = 400, message = "ReservedModifyOrdered Failure") })
	public ResponseEntity<?> modifiedReservedCan(@RequestBody ModifyReserveOrderDto reserve) throws ServiceException {
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

	/*
	 * @PostMapping("modifiedReservedCans")
	 * 
	 * @ApiOperation("ModifiedReservedCansApi")
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 200, message = "ReservedModifyOrdered Success!!",
	 * response = Message.class),
	 * 
	 * @ApiResponse(code = 400, message = "ReservedModifyOrdered Failure") }) public
	 * ResponseEntity<?> modifiedReservedCans(@RequestBody ModifyReserveDto reserve)
	 * throws ServiceException { String errorMessage = null; ReserveDetails orderCan
	 * = null; try { orderCan = reserveCanService.modifiedReserveCans(reserve); }
	 * catch (Exception e) { errorMessage = e.getMessage(); } if (orderCan != null)
	 * { return new ResponseEntity<>(orderCan, HttpStatus.OK); } else { Message
	 * message = new Message(errorMessage); return new ResponseEntity<>(message,
	 * HttpStatus.BAD_REQUEST); } }
	 */

	@GetMapping("viewReserveOrders")
	@ApiOperation(value = "ViewReserveOrders API")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ReservedModifyOrdered Success!!", response = Message.class),
			@ApiResponse(code = 400, message = "ReservedModifyOrdered Failure") })
	public ResponseEntity<?> viewReserveOrders() {
		List<ReserveDetails> list = null;
		String errorMessage = null;

		try {
			list = reserveCanService.viewReserveOrders();
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
		if (list != null)
			return new ResponseEntity<>(list, HttpStatus.OK);
		else
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @GetMapping("viewUserReserveOrders")
	 * 
	 * @ApiOperation(value = "ViewUserReserveOrders API")
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(code = 200, message = "ReservedModifyOrdered Success!!",
	 * response = Message.class),
	 * 
	 * @ApiResponse(code = 400, message = "ReservedModifyOrdered Failure") }) public
	 * ResponseEntity<?> viewUserReserveOrders() { List<ReserveDetails> list = null;
	 * String errorMessage = null;
	 * 
	 * try { list = reserveCanService.viewUserReserveOrders(); } catch (Exception e)
	 * { e.printStackTrace(); errorMessage = e.getMessage(); } if (list != null)
	 * return new ResponseEntity<>(list, HttpStatus.OK); else return new
	 * ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); }
	 */
	/*
	 * @PostMapping("cancelReserveOrder")
	 * 
	 * @ApiOperation(value = "CancelReserveCan API")
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 201, message = "Success",
	 * response = Message.class),
	 * 
	 * @ApiResponse(code = 400, message = "Invalid", response = Message.class) })
	 * public ResponseEntity<?> cancelReserveOrder(@RequestBody ReserveDto reserve)
	 * { String errorMessage = null; ReserveDetails reserveCan = null; try {
	 * reserveCan = reserveCanService.cancelReserve(reserve); } catch (Exception e)
	 * { errorMessage = e.getMessage(); } if (reserveCan != null) {
	 * 
	 * return new ResponseEntity<>(reserveCan, HttpStatus.OK); } else { errorMessage
	 * = "Invalid Reserve ID"; Message message = new Message(errorMessage); return
	 * new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); } }
	 */
}

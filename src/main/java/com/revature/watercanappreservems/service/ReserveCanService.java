package com.revature.watercanappreservems.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.watercanappreservems.dto.MessageConstant;
import com.revature.watercanappreservems.dto.ModifyReserveOrderDto;
import com.revature.watercanappreservems.exception.ServiceException;
import com.revature.watercanappreservems.dto.ReserveDto;
import com.revature.watercanappreservems.dto.ReserveOrderDto;
import com.revature.watercanappreservems.dto.StockDto;
import com.revature.watercanappreservems.model.ReserveDetails;
import com.revature.watercanappreservems.repository.ReserveCanRepository;

@Service
public class ReserveCanService {

	@Autowired
	private ReserveCanRepository reserveCanRepository;

	@Autowired
	private StockService stockService;

	public ReserveDetails reserveCan(ReserveDto reserve) throws ServiceException {
		ReserveDetails cans = null;
		ReserveDetails result = new ReserveDetails();
		result.setReservedCans(reserve.getReservedCans());
		result.setUserId(reserve.getUserId());
		List<StockDto> stockList = stockService.findAllStocks();
		StockDto stockAvailability = stockList.get(0);
		int stockCans = stockAvailability.getAvailableCans();
		ReserveDetails reserveId = null;
		if (reserve.getReservedCans() <= stockCans) {
			reserveId = reserveCanRepository.findByRepeatId(reserve.getUserId());
			if (reserveId == null) {
				result.setReservedCans(reserve.getReservedCans());
				result.setUserId(reserve.getUserId());
				result.setUserName(reserve.getUserName());
				result.setStatus("Reserved");
				result.setDate(LocalDateTime.now());
				cans = reserveCanRepository.save(result);
				stockService.addReservedStocks(reserve);
			} else {
				throw new ServiceException(MessageConstant.INVALID_RESERVEORDER);
			}
		} else {
			throw new ServiceException(MessageConstant.INVALID_MODIFY_CANS);
		}
		return cans;
	}

	/*
	 * public ReserveDetails reserveStock(ReserveDto reserve) throws
	 * ServiceException { ReserveDetails cans = null; ReserveDetails result = new
	 * ReserveDetails(); result.setReservedCans(reserve.getReservedCans());
	 * result.setUserId(reserve.getUserId()); List<StockDto> stockList =
	 * stockService.findAllStocks(); StockDto stockAvailability = stockList.get(0);
	 * int stockCans = stockAvailability.getAvailableCans(); if
	 * (reserve.getReservedCans() <= stockCans) {
	 * result.setReservedCans(reserve.getReservedCans());
	 * result.setUserId(reserve.getUserId());
	 * result.setUserName(reserve.getUserName()); result.setStatus("Reserved");
	 * result.setDate(LocalDateTime.now()); cans =
	 * reserveCanRepository.save(result); stockService.addReservedStocks(reserve); }
	 * else { throw new ServiceException(MessageConstant.INVALID_CANS); } return
	 * cans; }
	 */
	/*
	 * try { MailDto mail = new MailDto(); ReserveDto orderDTO = new ReserveDto();
	 * mail.setName(orderDTO.getUserName()); mail.setEmail(orderDTO.getEmail());
	 * MailService mailservice=new MailService(); mailservice.sendMail(mail); }
	 * catch (Exception e) { e.printStackTrace(); throw new
	 * ServiceException("Unable to Order"); } return cans; }
	 */
	public ReserveDetails reserveorderCan(ReserveOrderDto reserve) throws ServiceException {
		ReserveDetails orderCanValue = null;
		ReserveDetails cans = reserveCanRepository.findByReserveId(reserve.getReserveId());
		if (cans != null) {
			int can = cans.getReservedCans();
			ReserveDetails orderCan = new ReserveDetails();
			orderCan.setReservedOrderCans(can);
			orderCan.setReservedCans(can);
			orderCan.setUserId(cans.getUserId());
			orderCan.setUserName(cans.getUserName());
			orderCan.setStatus("Ordered");
			orderCan.setDate(LocalDateTime.now());
			orderCanValue = reserveCanRepository.save(orderCan);
			ReserveDetails reserveCan = new ReserveDetails();
			reserveCan.setReserveId(reserve.getReserveId());
			reserveCanRepository.delete(reserveCan);
		} else {
			throw new ServiceException(MessageConstant.INVALID_RESERVEID);
		}
		return orderCanValue;
	}

	public ReserveDetails modifiedReserveCan(ModifyReserveOrderDto reserve) throws ServiceException {
		ReserveDetails orderCanValue = null;
		ReserveDetails result = null;
		result = reserveCanRepository.findByReserveOrderId(reserve.getReserveId());
		if (result != null) {
			if (result.getReserveId() == reserve.getReserveId()) {
				List<StockDto> stockList = stockService.findAllStocks();
				StockDto stockAvailability = stockList.get(0);
				int stockCans = stockAvailability.getAvailableCans();
				int reserveCan = reserve.getReservedOrderCans();
				if (reserveCan < stockCans) {
					if (reserve.getReservedOrderCans() < result.getReservedCans()) {
						ReserveDetails orderCan = new ReserveDetails();
						orderCan.setReservedCans(result.getReservedCans());
						orderCan.setReservedOrderCans(reserve.getReservedOrderCans());
						orderCan.setUserId(result.getUserId());
						orderCan.setUserName(result.getUserName());
						orderCan.setStatus("Ordered");
						orderCan.setDate(LocalDateTime.now());
						orderCanValue = reserveCanRepository.save(orderCan);
						ReserveDetails reserveCans = new ReserveDetails();
						reserveCans.setReserveId(reserve.getReserveId());
						reserveCanRepository.delete(reserveCans);
						stockService.addReservedOrderedStocks(reserve);
					} else if (reserve.getReservedOrderCans() > result.getReservedCans()) {
						ReserveDetails orderCan = new ReserveDetails();
						orderCan.setReservedCans(result.getReservedCans());
						orderCan.setReservedOrderCans(reserve.getReservedOrderCans());
						orderCan.setUserId(result.getUserId());
						orderCan.setUserName(result.getUserName());
						orderCan.setStatus("Ordered");
						orderCan.setDate(LocalDateTime.now());
						orderCanValue = reserveCanRepository.save(orderCan);
						ReserveDetails reserveCans = new ReserveDetails();
						reserveCans.setReserveId(reserve.getReserveId());
						reserveCanRepository.delete(reserveCans);
						stockService.subReservedOrderedStocks(reserve);
					} else {
						throw new ServiceException(MessageConstant.INVALID_CANS);
					}
				} else {
					throw new ServiceException(MessageConstant.INVALID_MODIFY_CANS);
				}
			} else {
				throw new ServiceException(MessageConstant.INVALID_RESERVEID);
			}
		} else {
			throw new ServiceException(MessageConstant.INVALID_RESERVEID);
		}
		return orderCanValue;
	}

	/*
	 * public ReserveDetails modifiedReserveCans(ModifyReserveDto reserve) throws
	 * ServiceException { ReserveDetails result = null; result =
	 * reserveCanRepository.findByReserveOrderId(reserve.getReserveId()); if (result
	 * != null) { if (result.getReserveId() == reserve.getReserveId()) { if
	 * (reserve.getReservedOrderCans() < result.getReservedCans()) {
	 * stockService.passingCans(result.getReservedCans()); } else if
	 * (reserve.getReservedOrderCans() > result.getReservedCans()) {
	 * stockService.passingCans(result.getReservedCans()); } } } return result; }
	 */

	public List<ReserveDetails> viewReserveOrders() throws ServiceException {
		List<ReserveDetails> list = null;
		list = reserveCanRepository.findAll();
		if (list == null) {
			throw new ServiceException(MessageConstant.INVALID_RESERVEORDERS);
		}
		return list;
	}
}

/*
 * public List<ReserveDetails> viewUserReserveOrders() throws ServiceException {
 * List<ReserveDetails> list = null; list =
 * reserveCanRepository.findById(reserve.getUserId()); if (list == null) { throw
 * new ServiceException(MessageConstant.INVALID_RESERVEORDERS); } return list; }
 * }
 */

/*
 * public ReserveDetails cancelReserve(ReserveDto reserve) throws
 * ServiceException { ReserveDetails cans =
 * reserveCanRepository.findByCancelId(reserve.getReserveId()); if
 * (cans.getUserId() != reserve.getUserId() && cans.getStatus() != "Reserved") {
 * ReserveDetails canId = new ReserveDetails();
 * canId.setReserveId(reserve.getReserveId());
 * reserveCanRepository.delete(canId); stockService.addCancelStock(cans); } else
 * if (cans.getUserName() == reserve.getUserName() && cans.getStatus() ==
 * "Reserved") { throw new ServiceException("Invalid ReserveId"); } return cans;
 * }
 */

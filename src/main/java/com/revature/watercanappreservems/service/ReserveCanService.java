package com.revature.watercanappreservems.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.watercanappreservems.dto.MessageConstant;
import com.revature.watercanappreservems.exception.ServiceException;
import com.revature.watercanappreservems.dto.ReserveDto;
import com.revature.watercanappreservems.dto.StockDto;
import com.revature.watercanappreservems.model.ReserveDetails;
import com.revature.watercanappreservems.repository.ReserveCanRepository;

@Service
public class ReserveCanService {

	@Autowired
	private ReserveCanRepository reserveCanRepository;
	
	@Autowired
	private StockService stockService;


	public ReserveDetails reserveStock(ReserveDto reserve) throws ServiceException {
		ReserveDetails cans = null;
		ReserveDetails result = new ReserveDetails();
		result.setReservedCans(reserve.getReservedCans());
		result.setUserId(reserve.getUserId());
        List<StockDto> stockList=stockService.findAllStocks();
        StockDto stockAvailability = stockList.get(0);
        int stockCans=stockAvailability.getAvailableCans();
        if (reserve.getReservedCans() <= stockCans) {
			result.setReservedCans(reserve.getReservedCans());
			result.setUserId(reserve.getUserId());
			result.setUserName(reserve.getUserName());
			result.setStatus("Reserved");
			result.setDate(LocalDateTime.now());
			cans = reserveCanRepository.save(result);
			 stockService.addReservedStocks(reserve);
		} else {
			throw new ServiceException("Invalid cans...please check available stock and re enter the value");
		}
		return cans;
	}

	public ReserveDetails reserveorderCan(Integer reserveId) throws ServiceException {
		ReserveDetails orderCanValue = null;
		ReserveDetails cans = reserveCanRepository.findByReserveId(reserveId);
		if (cans != null) {
			int can = cans.getReservedCans();
			ReserveDetails orderCan = new ReserveDetails();
			orderCan.setReservedOrderCans(can);
			orderCan.setReservedCans(can);
			orderCan.setUserName(cans.getUserName());
			orderCan.setStatus("Ordered");
			orderCan.setDate(LocalDateTime.now());
			orderCanValue = reserveCanRepository.save(orderCan);
			ReserveDetails reserveCan = new ReserveDetails();
			reserveCan.setReserveId(reserveId);
			reserveCanRepository.delete(reserveCan);
		} else {
			throw new ServiceException(MessageConstant.INVALID_RESERVEID);
		}
		return orderCanValue;
	}

	public ReserveDetails modifiedReserveCan(ReserveDto reserve) throws ServiceException {
		ReserveDetails orderCanValue = null;
		ReserveDetails result = null;
		result = reserveCanRepository.findByReserveOrderId(reserve.getReserveId());
		if (result != null) {
			if (result.getReserveId() == reserve.getReserveId()) {
				if (reserve.getReservedOrderCans() < result.getReservedCans()) {
					ReserveDetails orderCan = new ReserveDetails();
					orderCan.setReservedOrderCans(reserve.getReservedOrderCans());
					orderCan.setReservedCans(result.getReservedCans());
					orderCan.setUserId(result.getUserId());
					orderCan.setUserName(result.getUserName());
					orderCan.setStatus("Ordered");
					orderCan.setDate(LocalDateTime.now());
					orderCanValue = reserveCanRepository.save(orderCan);
					ReserveDetails reserveCans = new ReserveDetails();
					reserveCans.setReserveId(reserve.getReserveId());
					reserveCanRepository.delete(reserveCans);
					List<StockDto> stockList=stockService.findAllStocks();
			        StockDto stockAvailability = stockList.get(0);
			        int stockCans=stockAvailability.getAvailableCans();
			        stockService.addReservedStocks(reserve);
				} else if (reserve.getReservedOrderCans() > result.getReservedCans()){
					ReserveDetails orderCan = new ReserveDetails();
					orderCan.setReservedOrderCans(reserve.getReservedOrderCans());
					orderCan.setReservedCans(result.getReservedCans());
					orderCan.setUserId(result.getUserId());
					orderCan.setUserName(result.getUserName());
					orderCan.setStatus("Ordered");
					orderCan.setDate(LocalDateTime.now());
					orderCanValue = reserveCanRepository.save(orderCan);
					ReserveDetails reserveCans = new ReserveDetails();
					reserveCans.setReserveId(reserve.getReserveId());
					reserveCanRepository.delete(reserveCans);
					List<StockDto> stockList=stockService.findAllStocks();
			        StockDto stockAvailability = stockList.get(0);
			        int stockCans=stockAvailability.getAvailableCans();
			        stockService.addReservedStocks(reserve);
				}
				else {
					throw new ServiceException(MessageConstant.INVALID_CANS);
				}
			} else {
				throw new ServiceException(MessageConstant.INVALID_RESERVEID);
			}
		} else {
			throw new ServiceException(MessageConstant.INVALID_RESERVEID);
		}
		return orderCanValue;
	}

}
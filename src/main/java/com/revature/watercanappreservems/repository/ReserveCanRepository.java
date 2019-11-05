package com.revature.watercanappreservems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.revature.watercanappreservems.model.ReserveDetails;

@Repository

public interface ReserveCanRepository extends JpaRepository<ReserveDetails, String> {

	@Query(" from ReserveDetails where id = :id")
	ReserveDetails findByReserveId(@Param("id") int id);

	@Query(" from ReserveDetails where id = :id")
	ReserveDetails findByReserveOrderId(@Param("id") int id);

	@Query(" from ReserveDetails where userId = :userId and reservedOrderCans = 0")
	ReserveDetails findByRepeatId(@Param("userId")int userId);
	
	@Query(" from ReserveDetails where reserveId = :reserveId")
	ReserveDetails findByCancelId(@Param("reserveId")int reserveId);
	
	@Query(" from ReserveDetails where reserveId = :reserveId")
	List<ReserveDetails> findById(@Param("reserveId")int reserveId);
}

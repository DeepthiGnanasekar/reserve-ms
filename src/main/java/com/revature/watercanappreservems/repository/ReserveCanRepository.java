package com.revature.watercanappreservems.repository;

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

}

package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long> {
	
	Optional<Contract> findByName(String nameContract);

	Page<Contract> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
	

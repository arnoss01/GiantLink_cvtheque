package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	Optional<Campaign> findByName(String name);

	Page<Campaign> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

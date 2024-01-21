package com.giantlink.intranet.models.requests;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignRequest {

	@Size(min = 3, max = 45)
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	private Integer nbPositions;
	
	@Column(nullable = false)
	private Date closingDate;

	private Set<Long> regionsIds;

}

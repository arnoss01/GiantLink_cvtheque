package com.giantlink.intranet.models.responses;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignResponse {

	private Long id;
	private String name;
	private Integer nbPositions;
	private String description;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date closingDate;

	private Set<FunctionResponse> functions;
	private Set<ContractResponse> contracts;
	private Set<RegionResponse> regions;
}

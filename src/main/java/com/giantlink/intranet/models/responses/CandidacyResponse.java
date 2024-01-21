package com.giantlink.intranet.models.responses;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidacyResponse {

	private Long id;
	private String applicationName;
	private Date candidacyDate;
	
}

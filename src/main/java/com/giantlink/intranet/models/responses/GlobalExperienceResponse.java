package com.giantlink.intranet.models.responses;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalExperienceResponse {
	private Long id;
	private String campanyName;

	private String description;

	private Date startDate;
	private Date endDate;
}

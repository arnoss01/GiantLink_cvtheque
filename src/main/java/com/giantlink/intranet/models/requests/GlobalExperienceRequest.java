package com.giantlink.intranet.models.requests;

import java.util.Date;

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
public class GlobalExperienceRequest {

	@Size(min = 2, max = 50)
	@Column(nullable = false)
	private String campanyName;

	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Date startDate;
	@Column(nullable = false)
	private Date endDate;
	
	@Column(nullable = false)
	private Long cvId;
}

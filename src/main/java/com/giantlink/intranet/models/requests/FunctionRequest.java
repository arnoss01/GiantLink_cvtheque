package com.giantlink.intranet.models.requests;

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
public class FunctionRequest {

	@Size(min = 3, max = 45)
	@Column(nullable = false)
	private String name;

	@Size(min = 3, max = 100)
	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Long campaignId;
}

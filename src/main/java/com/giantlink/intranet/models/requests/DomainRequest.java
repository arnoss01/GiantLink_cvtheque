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
public class DomainRequest {

	private Integer duration;

	@Column(nullable = false)
	@Size(min = 2, max = 45)
	private String name;
}

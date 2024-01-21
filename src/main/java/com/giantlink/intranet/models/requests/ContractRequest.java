package com.giantlink.intranet.models.requests;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest {
	
	@Size(min = 3, max = 45)
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;
	
	@NotNull()
	@Valid
	private Long campaignId;
	 
}

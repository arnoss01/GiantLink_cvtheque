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
public class CandidacyRequest {

	@Size(min = 3, max = 45)
	@Column(nullable = false)
	private String applicationName;
	private Date candidacyDate;
	
	@Column(nullable = false)
	private Long idCampaign;
	
	@Column(nullable = false)
	private Long idCv;

	private Set<Long> idQualifications;

}
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
public class QualificationRequest {

	@Size(min = 3, max = 25)
	@Column(nullable = false)
	private String name;

	@Size(min = 3, max = 300)
	@Column(nullable = false)
	private String wording;

	@Column(nullable = false)
	private Long candidacyId;

}

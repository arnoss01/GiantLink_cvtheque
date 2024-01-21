package com.giantlink.intranet.models.requests;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvRequest {

	@Size(min = 2, max = 45)
	@Column(nullable = false)
	private String availability;

	@Column(nullable = false)
	private String comment;

	private List<Long> domainsId;

	@NotNull()
	private Long candidateId;

	private List<Long> skillsId;

	private List<Long> educationsId;

	private List<Long> languagesId;

	private List<Long> candidaciesId;
	
	private List<GlobalExperienceRequest> experienceRequests;
}

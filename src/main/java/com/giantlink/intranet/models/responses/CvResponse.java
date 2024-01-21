package com.giantlink.intranet.models.responses;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CvResponse {

	private Long id;
	private String availability;
	private String comment;

	private CandidateResponse candidate;
	//private Set<CandidacyResponse> candidacies;

	private Set<SkillsResponse> skills;
	private Set<EducationResponse> educations;
	private Set<GlobalExperienceResponse> globalExperiences;
	private Set<LanguageResponse> languages;
	private Set<DomainResponse> domains;

}

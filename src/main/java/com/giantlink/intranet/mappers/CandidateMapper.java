package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Candidate;
import com.giantlink.intranet.models.requests.CandidateRequest;
import com.giantlink.intranet.models.responses.CandidateResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidateMapper {

	CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

	Candidate candidateRequestToCandidate(CandidateRequest candidateRequest);

	CandidateResponse candidateTocandidateResponse(Candidate candidate);

	Set<CandidateResponse> mapCandidate(Set<Candidate> availablities);

	List<CandidateResponse> mapCandidate(List<Candidate> availablities);

}

package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Candidacy;
import com.giantlink.intranet.models.requests.CandidacyRequest;
import com.giantlink.intranet.models.responses.CandidacyResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CandidacyMapper {

	CandidacyMapper INSTANCE = Mappers.getMapper(CandidacyMapper.class);

	Candidacy candidacyRequestToCandidacy(CandidacyRequest candidacyRequest);

	CandidacyResponse candidacyToCandidacyResponse(Candidacy postulation);

	Set<CandidacyResponse> mapResponses(Set<Candidacy> candidacies);

	List<CandidacyResponse> mapResponses(List<Candidacy> candidacies);

}

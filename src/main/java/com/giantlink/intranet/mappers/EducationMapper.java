package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Education;
import com.giantlink.intranet.models.requests.EducationRequest;
import com.giantlink.intranet.models.responses.EducationResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EducationMapper {

	EducationMapper INSTANCE = Mappers.getMapper(EducationMapper.class);

	Education EducationRequestToEducation(EducationRequest educationRequest);

	EducationResponse educationToEducationResponse(Education candidat);

	Set<EducationResponse> mapEducation(Set<Education> educationResponses);

	List<EducationResponse> mapEducation(List<Education> educationResponses);
}

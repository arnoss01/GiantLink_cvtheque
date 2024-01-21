package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.GlobalExperience;
import com.giantlink.intranet.models.requests.GlobalExperienceRequest;
import com.giantlink.intranet.models.responses.GlobalExperienceResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GlobalExperienceMapper {

	GlobalExperienceMapper INSTANCE = Mappers.getMapper(GlobalExperienceMapper.class);

	GlobalExperience globalExperienceRequestToGlobalExperience(GlobalExperienceRequest globalExperienceRequest);

	GlobalExperienceResponse globalExperienceToGlobalExperienceResponse(GlobalExperience candidat);

	Set<GlobalExperienceResponse> mapGlobalExperience(Set<GlobalExperience> experienceRequests);

	List<GlobalExperienceResponse> mapGlobalExperience(List<GlobalExperience> experienceRequests);
}

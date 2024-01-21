package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Skills;
import com.giantlink.intranet.models.requests.SkillsRequest;
import com.giantlink.intranet.models.responses.SkillsResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SkillsMapper {

	SkillsMapper INSTANCE = Mappers.getMapper(SkillsMapper.class);

	Skills skillsRequestToSkills(SkillsRequest skillsRequest);

	SkillsResponse skillToSkillResponse(Skills skill);

	Set<SkillsResponse> mapSkills(Set<Skills> skills);

	List<SkillsResponse> mapSkills(List<Skills> skills);

}

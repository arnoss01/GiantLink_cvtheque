package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Function;
import com.giantlink.intranet.models.requests.FunctionRequest;
import com.giantlink.intranet.models.responses.FunctionResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FunctionMapper {

	FunctionMapper INSTANCE = Mappers.getMapper(FunctionMapper.class);

	FunctionResponse functionToFunctionResponse(Function candidat);

	Function functionRequestToFunction(FunctionRequest functionRequest);

	Set<FunctionResponse> mapEducation(Set<Function> functionRequests);

	List<FunctionResponse> mapEducation(List<Function> functionRequests);
}

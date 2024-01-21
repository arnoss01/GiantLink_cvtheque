package com.giantlink.intranet.mappers;

import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Domain;
import com.giantlink.intranet.models.requests.DomainRequest;
import com.giantlink.intranet.models.responses.DomainResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DomainMapper {

	DomainMapper INSTANCE = Mappers.getMapper(DomainMapper.class);

	Domain domainRequestTodomain(DomainRequest domainRequest);

	DomainResponse domainToDomainResponse(Domain domain);

	Set<DomainResponse> mapDomain(Set<Domain> domains);
}

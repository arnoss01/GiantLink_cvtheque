package com.giantlink.intranet.mappers;

import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Region;
import com.giantlink.intranet.models.requests.RegionRequest;
import com.giantlink.intranet.models.responses.RegionResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegionMapper {

	RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

	Region regionRequestToRegion(RegionRequest regionRequest);

	RegionResponse regionToRegionResponse(Region region);

	Set<RegionResponse> mapRegion(Set<Region> regionRequests);

}

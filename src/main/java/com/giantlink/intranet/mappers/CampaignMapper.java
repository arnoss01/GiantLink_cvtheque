package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Campaign;
import com.giantlink.intranet.models.requests.CampaignRequest;
import com.giantlink.intranet.models.responses.CampaignResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CampaignMapper {
	CampaignMapper INSTANCE = Mappers.getMapper(CampaignMapper.class);

	Campaign campaignRequestToCampaign(CampaignRequest campaignRequest);

	CampaignResponse campaignToCampaignResponse(Campaign campaign);

	List<CampaignResponse> mapCampaign(List<Campaign> availablities);

	Set<CampaignResponse> mapCampaign(Set<Campaign> availablities);
}

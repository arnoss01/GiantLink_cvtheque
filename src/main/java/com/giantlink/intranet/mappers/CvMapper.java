package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Cv;
import com.giantlink.intranet.models.requests.CvRequest;
import com.giantlink.intranet.models.responses.CvResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CvMapper {

	CvMapper INSTANCE = Mappers.getMapper(CvMapper.class);

	Cv cvRequestToCv(CvRequest cvRequest);

	CvResponse cvToCvResponse(Cv cv);

	Set<CvResponse> mapCv(Set<Cv> cvs);

	List<CvResponse> mapListCv(List<Cv> cv);
}

package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Qualification;
import com.giantlink.intranet.models.requests.QualificationRequest;
import com.giantlink.intranet.models.responses.QualificationResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QualificationMapper {

	QualificationMapper INSTANCE = Mappers.getMapper(QualificationMapper.class);

	Qualification QualificationRequestToQualification(QualificationRequest qualificationRequest);

	QualificationResponse qualificationToQualificationResponse(Qualification qualification);

	Set<QualificationResponse> mapQualification(Set<Qualification> qualification);

	List<QualificationResponse> mapQualification(List<Qualification> qualification);
}

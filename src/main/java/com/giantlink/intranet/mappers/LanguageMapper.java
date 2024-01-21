package com.giantlink.intranet.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.giantlink.intranet.entities.Language;
import com.giantlink.intranet.models.requests.LanguageRequest;
import com.giantlink.intranet.models.responses.LanguageResponse;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper {

	LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

	Language LanguageRequestToLanguage(LanguageRequest LanguageRequest);

	LanguageResponse LanguageToLanguageResponse(Language skill);

	Set<LanguageResponse> mapLanguage(Set<Language> Language);

	List<LanguageResponse> mapLanguage(List<Language> Language);

}

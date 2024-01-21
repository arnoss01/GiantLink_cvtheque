package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Language;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.LanguageMapper;
import com.giantlink.intranet.models.requests.LanguageRequest;
import com.giantlink.intranet.models.responses.LanguageResponse;
import com.giantlink.intranet.repositories.LanguageRepository;
import com.giantlink.intranet.services.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	LanguageRepository languageRepository;

	@Override
	public LanguageResponse add(LanguageRequest languageRequest) throws GlAlreadyExistException {
		Optional<Language> languagefind = languageRepository.findByName(languageRequest.getName());
		if (languagefind.isPresent()) {
			throw new GlAlreadyExistException("language", Language.class.getSimpleName());
		}
		Language language = LanguageMapper.INSTANCE.LanguageRequestToLanguage(languageRequest);
		language.setCvs(null);

		return LanguageMapper.INSTANCE.LanguageToLanguageResponse(languageRepository.save(language));
	}

	@Override
	public List<LanguageResponse> getAll() {
		// TODO Auto-generated method stub
		return LanguageMapper.INSTANCE.mapLanguage(languageRepository.findAll());
	}

	@Override
	public LanguageResponse get(Long id) throws GlNotFoundException {
		Optional<Language> languagefind = languageRepository.findById(id);
		if (languagefind.isEmpty()) {
			throw new GlNotFoundException("language", Language.class.getSimpleName());
		}
		return LanguageMapper.INSTANCE.LanguageToLanguageResponse(languagefind.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Language> languagefind = languageRepository.findById(id);
		if (languagefind.isEmpty()) {
			throw new GlNotFoundException("language", Language.class.getSimpleName());
		}
		languageRepository.delete(languagefind.get());
	}

	@Override
	public LanguageResponse update(Long id, LanguageRequest languageRequest) throws GlNotFoundException {
		Optional<Language> languagefind = languageRepository.findById(id);
		if (languagefind.isEmpty()) {
			throw new GlNotFoundException("language", Language.class.getSimpleName());
		}
		languagefind.get().setName(languageRequest.getName());
		return LanguageMapper.INSTANCE.LanguageToLanguageResponse(languageRepository.save(languagefind.get()));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<LanguageResponse> languagesResponses = new ArrayList<>();
		Page<Language> languages = (name.isBlank()) ? languageRepository.findAll(pageable)
				: languageRepository.findByNameContainingIgnoreCase(name, pageable);
		languages.getContent().forEach(language -> {
			languagesResponses.add(LanguageMapper.INSTANCE.LanguageToLanguageResponse(language));
		});
		Map<String, Object> skillsResponse = new HashMap<>();
		skillsResponse.put("content", languagesResponses);
		skillsResponse.put("currentPage", languages.getNumber());
		skillsResponse.put("totalElements", languages.getTotalElements());
		skillsResponse.put("totalPages", languages.getTotalPages());
		return skillsResponse;
	}

}

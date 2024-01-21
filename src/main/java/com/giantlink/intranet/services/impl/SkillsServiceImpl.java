package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Skills;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.SkillsMapper;
import com.giantlink.intranet.models.requests.SkillsRequest;
import com.giantlink.intranet.models.responses.SkillsResponse;
import com.giantlink.intranet.repositories.SkillsRepository;
import com.giantlink.intranet.services.SkillsService;

@Service
public class SkillsServiceImpl implements SkillsService {

	@Autowired
	SkillsRepository skillsRepository;

	@Transactional
	@Override
	public SkillsResponse add(SkillsRequest skills) throws GlAlreadyExistException {
		Optional<Skills> findSkills = skillsRepository.findByName(skills.getName());
		if (findSkills.isPresent()) {
			throw new GlAlreadyExistException("Skills", Skills.class.getSimpleName());
		}
		Skills skill = SkillsMapper.INSTANCE.skillsRequestToSkills(skills);
		skill.setCvs(new HashSet<>());

		return SkillsMapper.INSTANCE.skillToSkillResponse(skillsRepository.save(skill));
	}

	@Override
	public List<SkillsResponse> getAll() {
		return SkillsMapper.INSTANCE.mapSkills(skillsRepository.findAll());
	}

	@Override
	public SkillsResponse get(Long id) throws GlNotFoundException {
		Optional<Skills> skill = skillsRepository.findById(id);
		if (skill.isEmpty()) {
			throw new GlNotFoundException("skill", Skills.class.getSimpleName());
		}
		return SkillsMapper.INSTANCE.skillToSkillResponse(skillsRepository.findById(id).get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Skills> skill = skillsRepository.findById(id);
		if (skill.isEmpty()) {
			throw new GlNotFoundException("skill", Skills.class.getSimpleName());
		}
		skillsRepository.deleteById(id);
	}

	@Override
	public SkillsResponse update(Long id, SkillsRequest skillsRequest) throws GlNotFoundException {
		Optional<Skills> skill = skillsRepository.findById(id);
		if (skill.isEmpty()) {
			throw new GlNotFoundException("skill", Skills.class.getSimpleName());
		}

		skill.get().setName(skillsRequest.getName());
		skill.get().setDescription(skillsRequest.getDescription());

		return SkillsMapper.INSTANCE.skillToSkillResponse(skillsRepository.save(skill.get()));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<SkillsResponse> skillsResponses = new ArrayList<>();
		Page<Skills> skills = (name.isBlank()) ? skillsRepository.findAll(pageable)
				: skillsRepository.findByNameContainingIgnoreCase(name, pageable);
		skills.getContent().forEach(skill -> {
			SkillsResponse response = SkillsResponse.builder().id(skill.getId()).description(skill.getDescription())
					.name(skill.getName()).build();

			skillsResponses.add(response);
		});
		Map<String, Object> skillsResponse = new HashMap<>();
		skillsResponse.put("content", skillsResponses);
		skillsResponse.put("currentPage", skills.getNumber());
		skillsResponse.put("totalElements", skills.getTotalElements());
		skillsResponse.put("totalPages", skills.getTotalPages());
		return skillsResponse;
	}
}

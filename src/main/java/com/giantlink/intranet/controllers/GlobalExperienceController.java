package com.giantlink.intranet.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giantlink.intranet.entities.GlobalExperience;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.GlobalExperienceRequest;
import com.giantlink.intranet.models.responses.GlobalExperienceResponse;
import com.giantlink.intranet.services.GlobalExperienceService;

@RestController
@RequestMapping("/api/experience")
@CrossOrigin(origins = { "http://localhost:4200" })
public class GlobalExperienceController {

	@Autowired
	GlobalExperienceService globalExperienceService;

	@PostMapping()
	public ResponseEntity<GlobalExperience> add(
			@RequestBody @Valid GlobalExperienceRequest globalExperienceRequest) throws GlAlreadyExistException {
		GlobalExperience GEadd = globalExperienceService.add(globalExperienceRequest);
		return new ResponseEntity<GlobalExperience>(GEadd, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(globalExperienceService.getAllPaginations(name, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GlobalExperienceResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<GlobalExperienceResponse>(globalExperienceService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		globalExperienceService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<GlobalExperienceResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid GlobalExperienceRequest globalExperienceRequest) throws GlNotFoundException {

		return new ResponseEntity<GlobalExperienceResponse>(globalExperienceService.update(id, globalExperienceRequest),
				HttpStatus.OK);
	}

}

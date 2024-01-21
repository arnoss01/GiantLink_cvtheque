package com.giantlink.intranet.controllers;

import java.util.List;
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

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.SkillsRequest;
import com.giantlink.intranet.models.responses.SkillsResponse;
import com.giantlink.intranet.services.SkillsService;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = { "http://localhost:4200" })
public class SkillsController {

	@Autowired
	SkillsService skillsService;

	@PostMapping()
	public ResponseEntity<SkillsResponse> add(@RequestBody @Valid SkillsRequest skillsRequest)
			throws GlAlreadyExistException {
		SkillsResponse Sadd = skillsService.add(skillsRequest);
		return new ResponseEntity<SkillsResponse>(Sadd, HttpStatus.CREATED);
	}

	// GET all with pagination
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(skillsService.getAllPaginations(name, pageable), HttpStatus.OK);

	}
	
	// GET all
	@GetMapping("/all")
	public ResponseEntity<List<SkillsResponse>> getAll() {
		return new ResponseEntity<>(skillsService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SkillsResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<SkillsResponse>(skillsService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		skillsService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<SkillsResponse> edit(@PathVariable("id") Long id, @RequestBody @Valid SkillsRequest skillsRequest)
			throws GlNotFoundException {
		return new ResponseEntity<SkillsResponse>(skillsService.update(id, skillsRequest), HttpStatus.OK);
	}

}

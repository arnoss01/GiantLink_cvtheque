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
import com.giantlink.intranet.models.requests.EducationRequest;
import com.giantlink.intranet.models.responses.EducationResponse;
import com.giantlink.intranet.services.EducationService;

@RestController
@RequestMapping("/api/education")
@CrossOrigin(origins = { "http://localhost:4200" })
public class EducationController {

	@Autowired
	EducationService educationService;

	@PostMapping()
	public ResponseEntity<EducationResponse> add(@RequestBody @Valid EducationRequest educationRequest)
			throws GlAlreadyExistException {
		EducationResponse Eadd = educationService.add(educationRequest);
		return new ResponseEntity<EducationResponse>(Eadd, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(educationService.getAllPaginations(name, pageable),
				HttpStatus.OK);
	}
	
	// GET all
	@GetMapping("/all")
	public ResponseEntity<List<EducationResponse>> getAll() {
		return new ResponseEntity<>(educationService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EducationResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<EducationResponse>(educationService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		educationService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EducationResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid EducationRequest educationRequest) throws GlNotFoundException {
		return new ResponseEntity<EducationResponse>(educationService.update(id, educationRequest), HttpStatus.OK);
	}
}

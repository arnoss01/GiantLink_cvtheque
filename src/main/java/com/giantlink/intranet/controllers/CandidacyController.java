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
import com.giantlink.intranet.models.requests.CandidacyRequest;
import com.giantlink.intranet.models.responses.CandidacyResponse;
import com.giantlink.intranet.services.CandidacyService;

@RestController
@RequestMapping("/api/candidacy")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CandidacyController {

	@Autowired
	CandidacyService candidacyService;

	@PostMapping()
	public ResponseEntity<CandidacyResponse> add(@RequestBody @Valid CandidacyRequest candidacyRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		CandidacyResponse Padd = candidacyService.add(candidacyRequest);
		return new ResponseEntity<CandidacyResponse>(Padd, HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CandidacyResponse>> getall() {
		return new ResponseEntity<List<CandidacyResponse>>(candidacyService.responses(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "", name = "status") String status) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(candidacyService.getAllPaginations(status, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CandidacyResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<CandidacyResponse>(candidacyService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		candidacyService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CandidacyResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid CandidacyRequest candidacyRequest) throws GlNotFoundException {
		return new ResponseEntity<CandidacyResponse>(candidacyService.update(id, candidacyRequest), HttpStatus.OK);
	}

}
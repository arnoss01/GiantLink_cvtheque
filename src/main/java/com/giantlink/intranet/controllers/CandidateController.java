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
import com.giantlink.intranet.models.requests.CandidateRequest;
import com.giantlink.intranet.models.responses.CandidateResponse;
import com.giantlink.intranet.services.CandidateService;

@RestController
@RequestMapping("/api/candidate")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CandidateController {

	@Autowired
	CandidateService candidatService;

	@PostMapping()
	public ResponseEntity<CandidateResponse> add(@RequestBody @Valid CandidateRequest candidatRequest)
			throws GlAlreadyExistException {
		CandidateResponse Cadd = candidatService.add(candidatRequest);
		return new ResponseEntity<CandidateResponse>(Cadd, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "email") String email) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(candidatService.getAllPaginations(email, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CandidateResponse>> getAll() {
		return new ResponseEntity<>(candidatService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CandidateResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<CandidateResponse>(candidatService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		candidatService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CandidateResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid CandidateRequest candidatRequest) throws GlNotFoundException {
		return new ResponseEntity<CandidateResponse>(candidatService.update(id, candidatRequest), HttpStatus.OK);
	}
}

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
import com.giantlink.intranet.models.requests.QualificationRequest;
import com.giantlink.intranet.models.responses.QualificationResponse;
import com.giantlink.intranet.services.QualificationService;

@RestController
@RequestMapping("/api/qualification")
@CrossOrigin(origins = { "http://localhost:4200" })
public class QualificationController {

	@Autowired
	QualificationService qualificationService;

	@PostMapping()
	public ResponseEntity<QualificationResponse> add(@RequestBody @Valid QualificationRequest qualificationRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		return new ResponseEntity<QualificationResponse>(qualificationService.add(qualificationRequest),
				HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(qualificationService.getAllPaginations(name, pageable),
				HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<List<QualificationResponse>> getall() {
		return new ResponseEntity<List<QualificationResponse>>(qualificationService.responses(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<QualificationResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<QualificationResponse>(qualificationService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		qualificationService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<QualificationResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid QualificationRequest QualificationRequest) throws GlNotFoundException {
		return new ResponseEntity<QualificationResponse>(qualificationService.update(id, QualificationRequest),
				HttpStatus.OK);
	}
}

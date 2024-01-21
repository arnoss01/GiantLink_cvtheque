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
import com.giantlink.intranet.models.requests.LanguageRequest;
import com.giantlink.intranet.models.responses.LanguageResponse;
import com.giantlink.intranet.services.LanguageService;

@RestController
@RequestMapping("/api/language")
@CrossOrigin(origins = { "http://localhost:4200" })
public class LanguageController {

	@Autowired
	LanguageService languageService;

	@PostMapping()
	public ResponseEntity<LanguageResponse> add(@RequestBody @Valid LanguageRequest LanguageRequest)
			throws GlAlreadyExistException {
		LanguageResponse Sadd = languageService.add(LanguageRequest);
		return new ResponseEntity<LanguageResponse>(Sadd, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(languageService.getAllPaginations(name, pageable),
				HttpStatus.OK);

	}
	
	// GET all
	@GetMapping("/all")
	public ResponseEntity<List<LanguageResponse>> getAll() {
		return new ResponseEntity<>(languageService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LanguageResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<LanguageResponse>(languageService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		languageService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LanguageResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid LanguageRequest LanguageRequest) throws GlNotFoundException {
		return new ResponseEntity<LanguageResponse>(languageService.update(id, LanguageRequest), HttpStatus.OK);
	}
}

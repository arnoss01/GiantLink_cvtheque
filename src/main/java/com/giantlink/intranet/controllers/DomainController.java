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
import com.giantlink.intranet.models.requests.DomainRequest;
import com.giantlink.intranet.models.responses.DomainResponse;
import com.giantlink.intranet.services.DomainService;

@RestController
@RequestMapping("/api/domain")
@CrossOrigin(origins = { "http://localhost:4200" })
public class DomainController {

	@Autowired
	DomainService domainService;

	// POST add
	@PostMapping()
	public ResponseEntity<DomainResponse> add(@RequestBody @Valid DomainRequest domainRequest)
			throws GlAlreadyExistException {
		DomainResponse Domadd = domainService.add(domainRequest);
		return new ResponseEntity<DomainResponse>(Domadd, HttpStatus.CREATED);
	}

	// GET all
	@GetMapping("/all")
	public ResponseEntity<List<DomainResponse>> getAll() {
		return new ResponseEntity<>(domainService.getAll(), HttpStatus.OK);
	}

	// GET all with pagination
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(domainService.getAllPaginations(name, pageable), HttpStatus.OK);
	}

	// GET byId
	@GetMapping("/{id}")
	public ResponseEntity<DomainResponse> getById(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<DomainResponse>(domainService.get(id), HttpStatus.OK);
	}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		domainService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	// PUT

}

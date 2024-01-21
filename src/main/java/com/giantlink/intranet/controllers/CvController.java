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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.CvRequest;
import com.giantlink.intranet.models.requests.CvSearch;
import com.giantlink.intranet.models.responses.CvResponse;
import com.giantlink.intranet.services.CvService;

@RestController
@RequestMapping("/api/cv")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CvController {

	@Autowired
	CvService cvService;

	// POST add
	@PostMapping
	public ResponseEntity<CvResponse> add(@RequestBody @Valid CvRequest cvRequest)
			throws GlNotFoundException, GlAlreadyExistException {
		CvResponse cvResponse = cvService.add(cvRequest);
		return new ResponseEntity<CvResponse>(cvResponse, HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CvResponse>> getall() {
		return new ResponseEntity<List<CvResponse>>(cvService.getAll(), HttpStatus.OK);
	}

	// GET all with pagination
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(cvService.getAllPaginations(name, pageable), HttpStatus.OK);
	}

	// GET byId
		@GetMapping("/{id}")
		public ResponseEntity<CvResponse> getById(@PathVariable Long id) throws GlNotFoundException {
			return new ResponseEntity<CvResponse>(cvService.get(id), HttpStatus.OK);
		}

	// DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		cvService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	// GET BY ENTITY
	@GetMapping("/search/{entity}/{name}")
	public ResponseEntity<List<CvResponse>> get(@PathVariable("entity") String entity,
			@PathVariable("name") String name) {
		return new ResponseEntity<List<CvResponse>>(cvService.getByEntity(entity, name), HttpStatus.OK);
	}
	
	//Search
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> searchByEntities(@RequestBody @Valid CvSearch search, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(cvService.searchByEntities(search, pageable), HttpStatus.OK);
	}
		
}

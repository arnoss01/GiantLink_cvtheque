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
import com.giantlink.intranet.models.requests.RegionRequest;
import com.giantlink.intranet.models.responses.RegionResponse;
import com.giantlink.intranet.services.RegionService;

@RestController
@RequestMapping("/api/region")
@CrossOrigin(origins = { "http://localhost:4200" })
public class RegionController {

	@Autowired
	RegionService regionService;

	// ------------------------------------------- Post
	// ------------------------------------------- //
	@PostMapping()
	public ResponseEntity<RegionResponse> add(@RequestBody @Valid RegionRequest regionRequest)
			throws GlAlreadyExistException {
		RegionResponse Radd = regionService.add(regionRequest);
		return new ResponseEntity<RegionResponse>(Radd, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<RegionResponse>> getAll() {
		return new ResponseEntity<>(regionService.getAll(), HttpStatus.OK);
	}

	// ------------------------------------------- Get//
	// ------------------------------------------- //
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(regionService.getAllPaginations(name, pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RegionResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<RegionResponse>(regionService.get(id), HttpStatus.OK);
	}

	// ------------------------------------------- Delete
	// ------------------------------------------- //
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		regionService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	// ------------------------------------------- Edit
	// ------------------------------------------- //
	@PutMapping("/{id}")
	public ResponseEntity<RegionResponse> edit(@PathVariable("id") Long id, @RequestBody @Valid RegionRequest regionRequest)
			throws GlNotFoundException {
		return new ResponseEntity<RegionResponse>(regionService.update(id, regionRequest), HttpStatus.OK);
	}
}

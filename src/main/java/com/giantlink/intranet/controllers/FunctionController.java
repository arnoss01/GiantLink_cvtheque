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

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.FunctionRequest;
import com.giantlink.intranet.models.responses.FunctionResponse;
import com.giantlink.intranet.services.FunctionService;

@RestController
@RequestMapping("/api/function")
@CrossOrigin(origins = { "http://localhost:4200" })
public class FunctionController {

	@Autowired
	FunctionService functionService;

	@PostMapping()
	public ResponseEntity<FunctionResponse> add(@RequestBody @Valid FunctionRequest functionRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		FunctionResponse Fadd = functionService.add(functionRequest);
		return new ResponseEntity<FunctionResponse>(Fadd, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(functionService.getAllPaginations(name, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FunctionResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<FunctionResponse>(functionService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		functionService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FunctionResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid FunctionRequest functionRequest) throws GlNotFoundException {
		return new ResponseEntity<FunctionResponse>(functionService.update(id, functionRequest), HttpStatus.OK);
	}

}
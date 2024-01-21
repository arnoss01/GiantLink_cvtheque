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
import com.giantlink.intranet.models.requests.ContractRequest;
import com.giantlink.intranet.models.responses.ContractResponse;
import com.giantlink.intranet.services.ContractService;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ContractController {

	@Autowired
	ContractService contractService;

	// ------------------------------------------- Post
	// ------------------------------------------- //

	@PostMapping()
	public ResponseEntity<ContractResponse> add(@RequestBody @Valid ContractRequest contractRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		ContractResponse Cadd = contractService.add(contractRequest);
		return new ResponseEntity<ContractResponse>(Cadd, HttpStatus.CREATED);
	}

	// ------------------------------------------- Get
	// ------------------------------------------- //
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(contractService.getAllPaginations(name, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ContractResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<ContractResponse>(contractService.get(id), HttpStatus.OK);
	}

	// ------------------------------------------- Delete
	// ------------------------------------------- //
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		contractService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	// ------------------------------------------- Edit
	// ------------------------------------------- //
	@PutMapping("/{id}")
	public ResponseEntity<ContractResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid ContractRequest contractRequest) throws GlNotFoundException {
		return new ResponseEntity<ContractResponse>(contractService.update(id, contractRequest), HttpStatus.OK);
	}

}

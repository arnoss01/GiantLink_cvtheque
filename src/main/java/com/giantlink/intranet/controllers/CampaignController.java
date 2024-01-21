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
import com.giantlink.intranet.models.requests.CampaignRequest;
import com.giantlink.intranet.models.responses.CampaignResponse;
import com.giantlink.intranet.services.CampaignService;

@RestController
@RequestMapping("/api/campaign")
@CrossOrigin(origins = { "http://localhost:4200" })
public class CampaignController {

	@Autowired
	CampaignService campaignService;

	@PostMapping()
	public ResponseEntity<CampaignResponse> add(@RequestBody @Valid CampaignRequest campaignRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		return new ResponseEntity<CampaignResponse>(campaignService.add(campaignRequest), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CampaignResponse>> getAll() {
		return new ResponseEntity<>(campaignService.getAll(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "", name = "name") String name) {
		Pageable pageable = PageRequest.of(page, size);
		return new ResponseEntity<Map<String, Object>>(campaignService.getAllPaginations(name, pageable),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CampaignResponse> getOne(@PathVariable Long id) throws GlNotFoundException {
		return new ResponseEntity<CampaignResponse>(campaignService.get(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws GlNotFoundException {
		campaignService.delete(id);
		return new ResponseEntity<String>("deleted !", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CampaignResponse> edit(@PathVariable("id") Long id,
			@RequestBody @Valid CampaignRequest campaignRequest) throws GlNotFoundException {
		return new ResponseEntity<CampaignResponse>(campaignService.update(id, campaignRequest), HttpStatus.OK);
	}
}
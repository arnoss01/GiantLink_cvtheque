package com.giantlink.intranet.models.requests;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateRequest {

	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phone;
	
	private String adress;
	private String city;
	private String country;
	private Date birthDate;
	private String availability;
	private String message;
	private String civility;
	private Set<Long> cvIds;

}

package com.giantlink.intranet.models.responses;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateResponse {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String adress;
	private String city;
	private String country;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date birthDate;
	private String civility;
	private String availability;
	private String message;
}

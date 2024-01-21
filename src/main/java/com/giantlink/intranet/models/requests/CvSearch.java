package com.giantlink.intranet.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CvSearch {

	private String availability;
	
	private String skillName;
	
	private String domainName;
	
	private String languageName;
	
	private String educationName;
}

package com.giantlink.intranet.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainResponse {
	
	private Long id;
	
	private Integer duration;
	
	private String name;
}

package com.giantlink.intranet.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QualificationResponse {
	private Long id;
	private String name;
	private String wording;
}

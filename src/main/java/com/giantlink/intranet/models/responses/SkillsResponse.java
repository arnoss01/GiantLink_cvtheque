package com.giantlink.intranet.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillsResponse {
    private Long id;
	private String name;
	private String description;

}

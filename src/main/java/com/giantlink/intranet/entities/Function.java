package com.giantlink.intranet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "function")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Function {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 3, max = 25)
	@Column(nullable = false)
	private String name;

	@Size(min = 3, max = 100)
	@Column(nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "campaign_id", nullable = false)
	private Campaign campaign;

}

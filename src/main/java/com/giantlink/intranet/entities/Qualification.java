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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "qualification")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Qualification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 3, max = 25)
	@Column(nullable = false)
	private String name;

	@Size(min = 3, max = 300)
	@Column(nullable = false)
	private String wording;

	@ManyToOne()
	@JoinColumn(name = "candidacy_id", nullable = false)
	@JsonBackReference
	private Candidacy candidacy;

}

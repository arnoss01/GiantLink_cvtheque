package com.giantlink.intranet.entities;

import java.util.Date;

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
@Table(name = "globalExperience")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class GlobalExperience {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 2, max = 50)
	@Column(nullable = false)
	private String campanyName;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Date startDate;
	@Column(nullable = false)
	private Date endDate;

	@ManyToOne
	@JoinColumn(name = "cv_id", nullable = false)
	@JsonBackReference(value="cv-globalExperience")
	private Cv cv;

}

package com.giantlink.intranet.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "candidacy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidacy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String applicationName;

	private Date candidacyDate;

	@OneToMany(mappedBy = "candidacy")
	@JsonManagedReference
	private Set<Qualification> qualifications;

	@ManyToOne
	@JoinColumn(name = "campaign_id", nullable = false)
	@JsonBackReference
	private Campaign campaign;

	@ManyToOne
	@JoinColumn(name = "cv_id", nullable = false)
	@JsonBackReference(value="cv-candidacy")
	private Cv cv;

}

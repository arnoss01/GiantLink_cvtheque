package com.giantlink.intranet.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cv {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 2, max = 45)
	@Column(nullable = false)
	private String availability;

	@Column(nullable = false)
	private String comment;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cv_domain", joinColumns = @JoinColumn(name = "cv_id"), inverseJoinColumns = @JoinColumn(name = "domain_id"))
	@JsonManagedReference(value="cv-domain")
	private Set<Domain> domains;

	@ManyToOne
	@JoinColumn(name = "candidate_id", nullable = false)
	@JsonBackReference(value="cv-candidate")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Candidate candidate;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cv_skill", joinColumns = @JoinColumn(name = "cv_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	@JsonManagedReference(value="cv-skill")
	private Set<Skills> skills;

	@JsonManagedReference(value="cv-globalExperience")
	@OneToMany(mappedBy = "cv",cascade=CascadeType.ALL)
	private Set<GlobalExperience> globalExperiences;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cv_education", joinColumns = @JoinColumn(name = "cv_id"), inverseJoinColumns = @JoinColumn(name = "education_id"))
	@JsonManagedReference(value="cv-education")
	private Set<Education> educations;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cv_language", joinColumns = @JoinColumn(name = "cv_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
	@JsonManagedReference(value="cv-language")
	private Set<Language> languages;

	@OneToMany(mappedBy = "campaign",cascade=CascadeType.ALL)
	@JsonManagedReference(value="cv-candidacy")
	private Set<Candidacy> candidacies;

}

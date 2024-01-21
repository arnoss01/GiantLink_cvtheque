package com.giantlink.intranet.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "skill")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Skills {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 45)
	@Column(nullable = false)
	private String name;

	@Size(min = 3, max = 45)
	@Column(nullable = false)
	private String description;
	
	@ManyToMany(mappedBy = "skills")
	@JsonBackReference(value="cv-skill")
	private Set<Cv> cvs;

}

package com.giantlink.intranet.entities;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "campaign")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Size(min = 3, max = 45)
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	private Integer nbPositions;

	private Date closingDate;

	@OneToMany(mappedBy = "campaign")
	private Set<Candidacy> candidacies;

	@OneToMany(mappedBy = "campaign",cascade=CascadeType.ALL)

	private Set<Function> functions;

	@OneToMany(mappedBy = "campaign",cascade=CascadeType.ALL)

	private Set<Contract> contracts;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "campaign_region", joinColumns = @JoinColumn(name = "compaign_id"), inverseJoinColumns = @JoinColumn(name = "region_id"))
	private Set<Region> regions;

}

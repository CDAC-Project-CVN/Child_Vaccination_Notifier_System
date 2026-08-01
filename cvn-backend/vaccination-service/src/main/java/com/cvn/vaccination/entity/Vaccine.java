package com.cvn.vaccination.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
//@Builder
@Entity
@Table(name = "vaccines")
@AttributeOverride(name = "id", column = @Column(name = "vaccine_id"))
public class Vaccine extends BaseClass {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(name = "disease_prevented", nullable = false, length = 100)
    private String diseasePrevented;

    @Column(name = "required_age_days", nullable = false)
    private Integer requiredAgeDays;

    @Column(name = "number_of_doses", nullable = false)
    private Integer numberOfDoses;

    @Column(nullable = false)
    private Boolean mandatory = true;

	public Vaccine(String name, String description, String diseasePrevented, Integer requiredAgeDays,
			Integer numberOfDoses, Boolean mandatory) {
		super();
		this.name = name;
		this.description = description;
		this.diseasePrevented = diseasePrevented;
		this.requiredAgeDays = requiredAgeDays;
		this.numberOfDoses = numberOfDoses;
		this.mandatory = mandatory;
	}
    
}
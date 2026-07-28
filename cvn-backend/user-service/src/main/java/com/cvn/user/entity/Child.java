package com.cvn.user.entity;

import com.cvn.user.enums.BloodGroup;
import com.cvn.user.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "myParent")
//@Builder
@Entity
@Table(name = "childrens")
@AttributeOverride(name = "id", column = @Column(name = "child_id"))
public class Child extends BaseClass {

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroup bloodGroup;

    @Column(name = "photo_url")
    private String photoUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent myParent;

    //@OneToMany(mappedBy = "myChilds", cascade = CascadeType.ALL)  // fetch = FetchType.EAGER
	//private List<Appointment> myAppointments = new ArrayList<>();
    
	public Child(String firstName, String lastName, LocalDate dateOfBirth, Gender gender, BloodGroup bloodGroup,
			String photoUrl) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.photoUrl = photoUrl;
	}
	
}

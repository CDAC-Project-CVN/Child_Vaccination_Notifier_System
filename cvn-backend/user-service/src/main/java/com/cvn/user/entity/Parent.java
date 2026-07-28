package com.cvn.user.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  
@ToString(callSuper = true, exclude = {"myUser", "myChild"})
//@Builder
@Entity
@Table(name = "parents")
public class Parent extends BaseClass {
	
	@OneToOne(fetch = FetchType.LAZY)     // cascade = CascadeType.ALL
	@JoinColumn(name = "parent_id", nullable = false, unique = true)
	@MapsId
	private User myUser;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;
	
	@OneToMany(mappedBy = "myParent", cascade = CascadeType.ALL)  // fetch = FetchType.EAGER
	private List<Child> myChild = new ArrayList<>();

	public Parent(User myUser, String firstName, String lastName) {
		super();
		this.myUser = myUser;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
}

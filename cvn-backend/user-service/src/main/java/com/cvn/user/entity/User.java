package com.cvn.user.entity;

import com.cvn.user.enums.Role;
import com.cvn.user.enums.UserStatus;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "password")  // callSuper = true
//@Builder
@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseClass{

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;
    
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pincode;

    private Double latitude;

    private Double longitude;

	public User(String email, String password, String phone, Role role, UserStatus status, String city, String state,
			String pincode, Double latitude, Double longitude) {
		super();
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.status = status;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
    
}

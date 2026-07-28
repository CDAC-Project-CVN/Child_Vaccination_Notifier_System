package com.cvn.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "refresh_token")
@AttributeOverride(name = "id", column = @Column(name = "token_id"))
public class RefreshToken extends BaseClass {
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean revoked;

	public RefreshToken(String token, LocalDateTime expiresAt, Boolean revoked) {
		super();
		this.token = token;
		this.expiresAt = expiresAt;
		this.revoked = revoked;
	}

}

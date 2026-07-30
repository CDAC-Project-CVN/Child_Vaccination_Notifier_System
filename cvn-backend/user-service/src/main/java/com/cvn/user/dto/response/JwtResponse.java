package com.cvn.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
	private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long userId;

    private String email;

    private String role;
}

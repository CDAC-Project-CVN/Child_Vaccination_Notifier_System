package com.cvn.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
	@NotBlank(message = "Reset token is required")
    private String resetToken;

    @NotBlank(message = "New password is required")
    private String newPassword;
}

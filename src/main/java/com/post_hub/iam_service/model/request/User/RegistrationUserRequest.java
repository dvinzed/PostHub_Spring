package com.post_hub.iam_service.model.request.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public class RegistrationUserRequest implements Serializable {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}

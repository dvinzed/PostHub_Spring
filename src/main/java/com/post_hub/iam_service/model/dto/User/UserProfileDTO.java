package com.post_hub.iam_service.model.dto.User;

import com.post_hub.iam_service.model.dto.Role.RoleDTO;
import com.post_hub.iam_service.model.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO implements Serializable {
    private Integer id;
    private String username;
    private String email;

    private RegistrationStatus registrationStatus;
    private LocalDateTime lastLogin;

    private String token;
    private String refreshToken;
    private List<RoleDTO> roles;
}

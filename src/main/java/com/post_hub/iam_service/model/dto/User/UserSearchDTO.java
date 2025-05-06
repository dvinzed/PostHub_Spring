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
public class UserSearchDTO implements Serializable {
    private Integer id;
    private String username;
    private String email;
    private LocalDateTime created;
    private LocalDateTime lastLogin;


    private RegistrationStatus registrationStatus;
    private List<RoleDTO> roles;
}

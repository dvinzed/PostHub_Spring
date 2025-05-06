package com.post_hub.iam_service.model.request.User;


import com.post_hub.iam_service.model.enums.RegistrationStatus;
import com.post_hub.iam_service.model.enums.UserSortField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchRequest {
    private String username;
    private String email;
    private RegistrationStatus registrationStatus;
    private String keyword;
    private Boolean deleted;
    private UserSortField sortField;

}

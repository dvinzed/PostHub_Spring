package com.post_hub.iam_service.security.validation;

import com.post_hub.iam_service.model.constants.ApiErrorMessage;
import com.post_hub.iam_service.model.enteties.Role;
import com.post_hub.iam_service.model.exception.InvalidDataException;
import com.post_hub.iam_service.model.exception.NotFoundExceptinon;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.service.model.UserRole;
import com.post_hub.iam_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepository userRepository;
    public void valideNewUser(String username, String email,String password,String confirmPassword){
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new InvalidDataException(ApiErrorMessage.USERNAME_ALREADY_EXISTS.getMessage(username ));
        });


        userRepository.findByEmail(email).ifPresent(user -> {
            throw new InvalidDataException(ApiErrorMessage.EMAIL_ALREADY_EXISTS.getMessage(email));
        });


        if(!password.equals(confirmPassword)){
            throw new InvalidDataException(ApiErrorMessage.MISMATCH_PASSWORDS.getMessage());
        }

        if(PasswordUtils.isNotValidPassword(password)){
            throw new InvalidDataException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }
    }
}

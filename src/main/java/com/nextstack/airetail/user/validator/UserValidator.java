package com.nextstack.airetail.user.validator;

import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.user.dto.request.UserRequest;
import com.nextstack.airetail.user.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for user operations.
 */
@Component
public class UserValidator {

    private final UserRepository userRepository;

    /**
     * Creates the validator with repository dependency.
     */
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request user request
     */
    public void validateCreate(UserRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BusinessException("Password is required when creating a user");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists: " + request.getEmail());
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      user identifier
     * @param request user request
     */
    public void validateUpdate(Long id, UserRequest request) {
        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), id)) {
            throw new BusinessException("Username already exists: " + request.getUsername());
        }
        if (userRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new BusinessException("Email already exists: " + request.getEmail());
        }
    }
}

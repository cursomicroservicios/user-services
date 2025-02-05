package com.deivid.microservices.handler;

import com.deivid.microservices.model.UserEntity;
import jakarta.validation.*;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RepositoryEventHandler
public class UserEntityEventHandler {

    private final Validator validator;

    public UserEntityEventHandler() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @HandleBeforeCreate
    public void validateUser(UserEntity user) {
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
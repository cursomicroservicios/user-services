package com.deivid.microservices.config;

import com.deivid.microservices.model.UserEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserEntityProcessor implements RepresentationModelProcessor<EntityModel<UserEntity>> {

    @Override
    public EntityModel<UserEntity> process(EntityModel<UserEntity> model) {
        return EntityModel.of(Objects.requireNonNull(model.getContent()));
    }
}

package com.deivid.microservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document(collection = "users")
public record UserEntity(
        @Id String id,
        @NotEmpty(message = "El campo name es obligatorio") String name,
        @NotEmpty(message = "El campo lastname es obligatorio") String lastname,
        @Indexed(unique = true) @NotEmpty(message = "El campo username es obligatorio") String username,
        @JsonIgnore @NotEmpty(message = "El campo password es obligatorio") String password,
        String email,
        @NotEmpty(message = "La lista de roles no puede estar vacia") List<String> roles
) {}

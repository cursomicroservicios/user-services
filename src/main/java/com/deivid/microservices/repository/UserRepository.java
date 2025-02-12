package com.deivid.microservices.repository;

import com.deivid.microservices.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "users", collectionResourceRel = "users", itemResourceRel = "user")
public interface UserRepository extends ListPagingAndSortingRepository<UserEntity, String>, MongoRepository<UserEntity, String> {

    @RestResource(path = "email")
    List<UserEntity> getAllByEmail(String email);

    @RestResource(path = "username")
    UserEntity findFirstByUsername(@Param("username") String username);

    @RestResource(path = "byRole")
    List<UserEntity> findByRoles(@Param("role") String role);
}

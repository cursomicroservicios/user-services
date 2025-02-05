package com.deivid.microservices;

import com.deivid.microservices.model.UserEntity;
import com.deivid.microservices.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;

@RequiredArgsConstructor
@EnableDiscoveryClient
@SpringBootApplication
public class UserServicesApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServicesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userRepository.save(UserEntity.builder()
                        .name("Deivid")
                        .lastname("Urrego")
                        .username("dhurrego")
                        .password("123456")
                        .email("deivid.urrego@prueba.com")
                        .roles(List.of("ROLE_ADMIN", "ROLE_USER"))
                .build());
    }
}

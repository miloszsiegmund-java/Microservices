package com.example.loginapiservices.config;

import com.example.loginapiservices.domain.model.Role;
import com.example.loginapiservices.domain.model.User;
import com.example.loginapiservices.repository.RoleRepository;
import com.example.loginapiservices.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Optional;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService, RoleRepository roleRepository) {
        return args -> {
            Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
            if (!roleUser.isPresent()) {
                roleRepository.save(new Role(null, "ROLE_USER"));
            }

            userService.save(User.builder()
                    .username("test22")
                    .password("123")
                    .build());


            userService.save(User.builder()
                    .username("test34")
                    .password("1234")
                    .build());
        };
    }

    @Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}

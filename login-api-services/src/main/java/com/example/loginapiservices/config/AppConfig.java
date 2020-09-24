package com.example.loginapiservices.config;

import com.example.loginapiservices.domain.model.Role;
import com.example.loginapiservices.domain.model.User;
import com.example.loginapiservices.repository.RoleRepository;
import com.example.loginapiservices.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserService userService, RoleRepository roleRepository){
        return args -> {
            Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
            if(!roleUser.isPresent()){
                roleRepository.save(new Role(null, "ROLE_USER"));
            }

            userService.save(User.builder()
                    .email("test1@wp.pl")
                    .password("123")
                    .build());


            userService.save(User.builder()
                    .email("test2@wp.pl")
                    .password("1234")
                    .build());

            userService.save(User.builder()
                    .email("test3@wp.pl")
                    .password("12345")
                    .build());
        };
    }
}

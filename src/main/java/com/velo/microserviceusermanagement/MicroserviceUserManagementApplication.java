package com.velo.microserviceusermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceUserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceUserManagementApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return null;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return false;
            }
        };
    }

}

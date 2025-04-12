package com.project.ITAM.Config;

import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;
import com.project.ITAM.Service.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner createDefaultAdmin(UsersService userService) {
        return args -> {
            if (userService.countUsers() == 0) {
                UsersRequest admin = new UsersRequest();
                admin.setFirstName("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword("admin@123");
                userService.createUser(admin);
            }
        };
    }
}

package com.project.ITAM.Service;

import com.project.ITAM.Model.Users;
import com.project.ITAM.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;
    private  PasswordEncoder passwordEncoder;

    public DatabaseUserDetailsService(UserRepo loginUserRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = loginUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from database
        Users userEntity = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

       /* // Convert UserEntity to Spring Security UserDetails
        Set<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // Assuming Role has a method `getName()`
                .collect(Collectors.toSet());*/

        // Convert UserEntity to Spring Security UserDetails
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword()) // Already hashed
            //    .authorities(authorities) // e.g., "USER" or "ADMIN"
                .build();
    }
}

package com.project.ITAM.Repository;

import com.project.ITAM.Model.SmtpConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface SmptConfigRepo extends JpaRepository<SmtpConfig,Long> {

    @Query(value = "SELECT * FROM SmtpConfig WHERE provider = :provider", nativeQuery = true)
    Optional<SmtpConfig> findByProvider(String provider);
}

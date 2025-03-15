package com.project.ITAM.Repository;

import com.project.ITAM.Model.SsoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@EnableJpaRepositories
public interface SSOConfigRepo extends JpaRepository<SsoConfig,Long> {

    @Query(value = "SELECT * FROM sso_config WHERE providerName = :providerName AND enabled = 'true'", nativeQuery = true)
    Optional<SsoConfig> findByProviderAndEnabled(@Param("providerName") String providerName);

}

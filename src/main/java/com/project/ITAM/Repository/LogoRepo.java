package com.project.ITAM.Repository;

import com.project.ITAM.Model.LogoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface LogoRepo extends JpaRepository<LogoEntity,Long> {

}

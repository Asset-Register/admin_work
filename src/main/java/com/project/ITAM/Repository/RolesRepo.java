package com.project.ITAM.Repository;

import com.project.ITAM.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface RolesRepo extends JpaRepository<Role,Long> {
}

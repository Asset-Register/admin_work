package com.project.ITAM.Repository;

import com.project.ITAM.Model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PermissionRepo extends JpaRepository<Permission,Long> {
}

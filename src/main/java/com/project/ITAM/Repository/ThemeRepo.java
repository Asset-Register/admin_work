package com.project.ITAM.Repository;

import com.project.ITAM.Model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ThemeRepo extends JpaRepository<Theme,Long> {
}

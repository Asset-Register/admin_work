package com.project.ITAM.Repository;

import com.project.ITAM.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface GroupRepo extends JpaRepository<Groups,Long> {
}

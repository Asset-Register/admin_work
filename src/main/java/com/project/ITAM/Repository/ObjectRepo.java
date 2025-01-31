package com.project.ITAM.Repository;

import com.project.ITAM.Model.ObjectEntity;
import com.project.ITAM.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ObjectRepo extends JpaRepository<ObjectEntity,Long> {
}

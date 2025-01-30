package com.project.ITAM.Repository;

import com.project.ITAM.Model.DashBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface DashBoardRepo extends JpaRepository<DashBoard,Long> {

    List<DashBoard> findByFolderId(Long folderId);
}

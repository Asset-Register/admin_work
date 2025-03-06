package com.project.ITAM.Repository;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface DashBoardRepo extends JpaRepository<DashBoard,Long> {

    @Query(value = "SELECT * FROM DashBoard WHERE folder_id = :folderId",nativeQuery = true)
    List<DashBoard> findByFolderId(Long folderId);

    @Query(value = "SELECT * FROM DashBoard WHERE objectId = :objectId",nativeQuery = true)
    List<DashBoard> findByObjectId(Long objectId);

    void deleteByFolder(Folder folder);
}

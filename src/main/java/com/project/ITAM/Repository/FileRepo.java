package com.project.ITAM.Repository;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface FileRepo extends JpaRepository<FileEntity,Long> {

    List<FileEntity> findByFolderId(Long folderId);
}

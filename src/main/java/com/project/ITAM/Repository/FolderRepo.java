package com.project.ITAM.Repository;

import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface FolderRepo extends JpaRepository<Folder,Long> {

    // Find all root folders (parentFolder is NULL)
    List<Folder> findByParentFolderIsNull();

    List<Folder> findByParentFolderId(Long parentId);

}

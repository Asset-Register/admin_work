package com.project.ITAM.Repository;

import com.project.ITAM.Model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface FolderRepo extends JpaRepository<Folder,Long> {

    // Find all root folders (parentFolder is NULL)
    List<Folder> findByParentFolderIsNull();

    List<Folder> findByParentFolderId(Long parentId);

    List<Folder> findByUserFolderId(Long userId);

    @Query("SELECT f FROM Folder f " +
            "LEFT JOIN f.allowedUsers u " +
            "WHERE f.folderType = 'PUBLIC' " +
            "   OR f.owner.id = :userId " +
            "   OR (f.folderType = 'RESTRICTED' AND u.id = :userId)")
    List<Folder> findAccessibleFolders(@Param("userId") Long userId);

}

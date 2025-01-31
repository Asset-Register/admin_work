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

    @Query("SELECT f FROM Folder f " +
            "LEFT JOIN f.allowedUsers u " +
            "WHERE( f.folderType = 'PUBLIC' " +
            "   OR f.user.id = :userId " +
            "   OR (f.folderType = 'RESTRICTED' AND u.id = :userId))")
    List<Folder> findAccessibleFolders(@Param("userId") Long userId);

    @Query(value = """
        SELECT DISTINCT f.*
        FROM folders f
        LEFT JOIN folder_users fu ON f.id = fu.folder_id
        LEFT JOIN folder_groups fg ON f.id = fg.folder_id
        LEFT JOIN folder_objects fo ON f.id = fo.folder_id
        LEFT JOIN users u ON (u.id = fu.user_id OR u.group_id = fg.group_id OR u.object_id = fo.object_id)
        WHERE 
            u.id = :userId
            OR f.owner_id = :userId
            OR f.folder_type = 'Public'
            OR (f.folder_type = 'Restricted' AND fu.user_id IS NOT NULL)
            OR (f.folder_type = 'Restricted' AND fg.group_id IS NOT NULL)
            OR (f.folder_type = 'Restricted' AND fo.object_id IS NOT NULL)
        """, nativeQuery = true)
    List<Folder> findAccessFolders(@Param("userId") Long userId);

}

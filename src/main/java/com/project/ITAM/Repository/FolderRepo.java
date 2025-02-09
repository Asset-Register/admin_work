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
            "WHERE (f.folderType = 'PUBLIC' " +
            "   OR f.user.id = :userId " +
            "   OR (f.folderType = 'RESTRICTED' AND u.id = :userId)) " +
            "AND f.parentFolder IS NULL")
    List<Folder> findAccessibleFolders(@Param("userId") Long userId);

    @Query(value = """
    SELECT DISTINCT f.*
    FROM folders f
    LEFT JOIN folder_access fu ON f.id = fu.folder_id
    LEFT JOIN folder_group_access fg ON f.id = fg.folder_id
    LEFT JOIN folder_object_access fo ON f.id = fo.folder_id
    LEFT JOIN users u ON (u.userId = fu.user_id OR u.groupId = fg.group_id OR u.objectId = fo.object_id)
    WHERE 
        (u.userId = :userId
        OR f.user_id = :userId
        OR f.folderType = 'PUBLIC'
        OR (f.folderType = 'RESTRICTED' AND fu.user_id IS NOT NULL)
        OR (f.folderType = 'RESTRICTED' AND fg.group_id IS NOT NULL)
        OR (f.folderType = 'RESTRICTED' AND fo.object_id IS NOT NULL))
        AND f.parent_id IS NULL
    """, nativeQuery = true)
    List<Folder> findAccessFolders(@Param("userId") Long userId);


   /* SELECT DISTINCT f.*
    FROM folders f
    LEFT JOIN folder_access fu ON f.id = fu.folder_id
    LEFT JOIN folder_group_access fg ON f.id = fg.folder_id

    LEFT JOIN users u ON (u.userId = fu.user_id OR u.groupId = fg.group_id )
    WHERE
    u.userId = 3  -- User must be the logged-in user
    OR f.user_id = 3  -- Condition 1: User is the owner
    OR f.folderType = 'PUBLIC'  -- Condition 2: Folder is public
    OR (f.folderType = 'RESTRICTED' AND fu.user_id IS NOT NULL)  -- Condition 3: User is explicitly granted access
    OR (f.folderType = 'RESTRICTED' AND fg.group_id IS NOT NULL);  -- Condition 4: User belongs to a group with access*/



}

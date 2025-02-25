package com.project.ITAM.Repository;

import com.project.ITAM.Model.FileEntity;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.SavedView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface SaveViewRepo extends JpaRepository<SavedView,Long> {

    List<SavedView> findByFolderId(Long folderId);


    void deleteByFolder(Folder folder);
}

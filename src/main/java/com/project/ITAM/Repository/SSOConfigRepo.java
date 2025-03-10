package com.project.ITAM.Repository;

import com.project.ITAM.Model.DashBoard;
import com.project.ITAM.Model.Folder;
import com.project.ITAM.Model.SsoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface SSOConfigRepo extends JpaRepository<SsoConfig,Long> {

}

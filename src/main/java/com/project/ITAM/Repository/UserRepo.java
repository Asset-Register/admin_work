package com.project.ITAM.Repository;

import com.project.ITAM.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<Users,Long> {

    /*@Modifying
    @Query("DELETE FROM user_roles ur WHERE ur.role_id = :roleId")
    void deleteByRoleId(@Param("roleId") Long roleId);*/

}

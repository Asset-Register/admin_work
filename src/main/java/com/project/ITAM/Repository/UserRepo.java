package com.project.ITAM.Repository;

import com.project.ITAM.Model.Users;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.groupMapped WHERE u.userId = :userId")
    Optional<Users> findByIdWithGroups(@Param("userId") Long userId);


}

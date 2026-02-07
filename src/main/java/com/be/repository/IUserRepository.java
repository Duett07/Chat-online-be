package com.be.repository;

import com.be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User findUserByUsername(String username);

//    @Query("SELECT u FROM User u WHERE u.id <> :currentUserId")
//    List<User> findAllExcept(UUID currentUserId);
}

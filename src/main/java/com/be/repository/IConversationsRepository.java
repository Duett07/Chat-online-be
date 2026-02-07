package com.be.repository;

import com.be.entity.Conversations;
import com.be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IConversationsRepository extends JpaRepository<Conversations, UUID> {

    @Query("SELECT c FROM Conversations c WHERE (c.user1 = : u1 AND c.user2 = :u2) OR (c.user1 = :u2 AND c.user2 = :u1)")
    Optional<Conversations> findBetween(User u1, User u2);

    @Query("SELECT c FROM Conversations c WHERE c.user1.id = :userId OR c.user2.id = :userId")
    List<Conversations> findAllByUser(UUID userId);
}

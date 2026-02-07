package com.be.repository;

import com.be.entity.Conversations;
import com.be.entity.Messages;
import com.be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMessagesRepository extends JpaRepository<Messages, UUID> {

    @Query("SELECT m FROM Messages m WHERE m.conversations.id = :conversationId ORDER BY m.displayOrder ASC")
    List<Messages> findAllByConversations(UUID conversationId);

    Messages findTopByConversationsOrderByCreatedAtDesc(Conversations conversation);

}

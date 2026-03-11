package com.be.repository;

import com.be.entity.MessagesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMessageStatusRepository extends JpaRepository<MessagesStatus, UUID> {
}

package com.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "NVARCHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

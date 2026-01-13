package com.be.entity;

import com.be.enums.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity{

    private String username;
    private String password;
    private String displayName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}

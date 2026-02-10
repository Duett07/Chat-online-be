package com.be.entity;

import com.be.enums.GenderStatus;
import com.be.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity{

    private String username;
    private String password;
    private String displayName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy = "user1")
    private List<Conversations> conversationsAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<Conversations> conversationsAsUser2;

    @OneToMany(mappedBy = "sender")
    private List<Messages> messageSend;

    @OneToMany(mappedBy = "receiver")
    private List<Messages> messageReceive;

    private String image;
    @Enumerated(EnumType.STRING)
    private GenderStatus gender;
    private LocalDate dateOfBirth;
}

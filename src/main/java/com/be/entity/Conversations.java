package com.be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "conversations")
@Data
public class Conversations extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    private String title;

    @OneToMany(mappedBy = "conversations")
    private List<Messages> messages;
}

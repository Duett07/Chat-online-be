package com.be.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "conversations")
    private List<Messages> messages;

    private LocalDateTime deleteAtUser1;
    private LocalDateTime deleteAtUser2;
}

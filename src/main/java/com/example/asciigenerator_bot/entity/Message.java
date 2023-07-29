package com.example.asciigenerator_bot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @Column(name = "message_id")
    private String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id")
    List<WordToSeconds> sentBySecList;

}

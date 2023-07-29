package com.example.asciigenerator_bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "word_to_seconds")
@AllArgsConstructor
@NoArgsConstructor
public class WordToSeconds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_to_seconds_id")
    private Long id;

    private Long seconds;
    private String message;

}

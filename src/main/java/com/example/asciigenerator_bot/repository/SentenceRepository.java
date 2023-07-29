package com.example.asciigenerator_bot.repository;

import com.example.asciigenerator_bot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRepository extends JpaRepository<Message,String> {
}

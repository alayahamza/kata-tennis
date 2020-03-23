package com.alenia.kata.tennis.repository;

import com.alenia.kata.tennis.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPlayerRepository extends JpaRepository<Player, Long> {
}

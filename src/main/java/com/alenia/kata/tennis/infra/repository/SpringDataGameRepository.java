package com.alenia.kata.tennis.infra.repository;

import com.alenia.kata.tennis.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataGameRepository extends JpaRepository<Game, Long> {
}

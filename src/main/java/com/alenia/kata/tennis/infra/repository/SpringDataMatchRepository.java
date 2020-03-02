package com.alenia.kata.tennis.infra.repository;

import com.alenia.kata.tennis.domain.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMatchRepository extends JpaRepository<Match, Long> {
}

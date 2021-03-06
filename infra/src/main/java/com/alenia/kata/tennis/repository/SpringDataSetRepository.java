package com.alenia.kata.tennis.repository;

import com.alenia.kata.tennis.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSetRepository extends JpaRepository<Set, Long> {
}

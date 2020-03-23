package com.alenia.kata.tennis.repository;

import com.alenia.kata.tennis.entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    Match save(Match match);

    Optional<Match> findById(long id);

    List<Match> findAll();
}

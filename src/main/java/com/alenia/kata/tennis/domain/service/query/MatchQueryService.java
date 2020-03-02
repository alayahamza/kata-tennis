package com.alenia.kata.tennis.domain.service.query;

import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.exception.TennisException;

import java.util.List;

public interface MatchQueryService {

    List<Match> findAll();

    Match findById(long matchId) throws TennisException;
}

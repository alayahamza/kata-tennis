package com.alenia.kata.tennis.service.query;

import com.alenia.kata.tennis.entity.Match;
import com.alenia.kata.tennis.exception.TennisException;

import java.util.List;

public interface MatchQueryService {

    List<Match> findAll();

    Match findById(long matchId) throws TennisException;
}

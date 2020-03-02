package com.alenia.kata.tennis.domain.service.command;

import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.exception.TennisException;

public interface MatchCommandService {

    Match create(long firstPlayerId, long secondPlayerId) throws TennisException;

    Match addPointToFirstPlayer(long matchId) throws TennisException;

    Match addPointToSecondPlayer(long matchId) throws TennisException;
}

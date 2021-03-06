package com.alenia.kata.tennis.service.command;

import com.alenia.kata.tennis.entity.Game;
import com.alenia.kata.tennis.entity.Match;
import com.alenia.kata.tennis.entity.Set;
import com.alenia.kata.tennis.exception.TennisException;

public interface MatchCommandService {

    Match create(long firstPlayerId, long secondPlayerId) throws TennisException;

    Match addPointToFirstPlayer(long matchId) throws TennisException;

    void checkScore(Match match, int player) throws TennisException;

    boolean isSetOver(Match match);

    Match addPointToSecondPlayer(long matchId) throws TennisException;

    void addPoint(Match match, int player);

    void initNewGame(Match match);

    boolean isGameOver(Match match);

    void updateSecondPlayerScore(Game lastGame, Set lastSet);

    void updateFirstPlayerScore(Game lastGame, Set lastSet);

    void checkSetWinner(Match match, int player);

    void checkSetForSecondPlayer(Match match);

    void checkSetForFirstPlayer(Match match);

    String calculateScore(Game lastGame, int player);

    String checkDeuce(Game lastGame, int player);

    void initNewSet(Match match);

    boolean isMatchOver(Match match);

    boolean isTieScore(Match match);

    void applyTieBreakRule(Match match, int player);

    void playerWinsSetAndMatch(Match match, int player);

    void playerWinsMatch(Match match, int player);

    void playerWinsSet(Match match, int player);
}

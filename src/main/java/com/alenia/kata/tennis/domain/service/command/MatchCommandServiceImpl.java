package com.alenia.kata.tennis.domain.service.command;

import com.alenia.kata.tennis.domain.entity.Game;
import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.entity.Set;
import com.alenia.kata.tennis.domain.exception.TennisException;
import com.alenia.kata.tennis.domain.repository.MatchRepository;
import com.alenia.kata.tennis.domain.service.query.MatchQueryService;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryService;
import com.alenia.kata.tennis.domain.utils.TennisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.alenia.kata.tennis.domain.constant.TennisConstants.*;

@Service
public class MatchCommandServiceImpl implements MatchCommandService {

    private final MatchQueryService matchQueryService;
    private final MatchRepository matchRepository;
    private final PlayerQueryService playerQueryService;

    @Autowired
    public MatchCommandServiceImpl(MatchQueryService matchQueryService, MatchRepository matchRepository,
                                   PlayerQueryService playerQueryService) {
        this.matchQueryService = matchQueryService;
        this.matchRepository = matchRepository;
        this.playerQueryService = playerQueryService;
    }

    @Override
    public Match create(long firstPlayerId, long secondPlayerId) throws TennisException {
        List<Game> games = new ArrayList<>();
        List<Set> sets = new ArrayList<>();

        games.add(Game.builder()
                .firstPlayerScore(SCORE_ZERO)
                .secondPlayerScore(SCORE_ZERO)
                .number(1)
                .build());
        sets.add(Set.builder()
                .games(games)
                .number(1)
                .build());
        Match match = Match.builder()
                .firstPlayer(playerQueryService.findById(firstPlayerId))
                .secondPlayer(playerQueryService.findById(secondPlayerId))
                .sets(sets)
                .comment(MATCH_STARTED)
                .build();
        return matchRepository.save(match);
    }

    @Override
    public Match addPointToFirstPlayer(long matchId) throws TennisException {
        Match match = matchQueryService.findById(matchId);
        Game lastGame = TennisUtil.getLastGame(match);
        isGameOver(lastGame);
        int firstPlayerScore = lastGame.getFirstPlayerScore();
        if (firstPlayerScore != SCORE_FORTY) {
            lastGame.setFirstPlayerScore(addPoint(firstPlayerScore));
        } else {
            lastGame.setComment(FIRST_PLAYER_GAME);
        }
        return matchRepository.save(match);
    }

    private void isGameOver(Game lastGame) throws TennisException {
        if (lastGame.getComment() != null) {
            throw new TennisException(GAME_WON_ERROR);
        }
    }

    @Override
    public Match addPointToSecondPlayer(long matchId) throws TennisException {
        Match match = matchQueryService.findById(matchId);
        Game lastGame = TennisUtil.getLastGame(match);
        isGameOver(lastGame);
        int secondPlayerScore = lastGame.getSecondPlayerScore();
        if (secondPlayerScore != SCORE_FORTY) {
            lastGame.setSecondPlayerScore(addPoint(secondPlayerScore));
        } else {
            lastGame.setComment(SECOND_PLAYER_GAME);
        }
        return matchRepository.save(match);
    }

    private int addPoint(int currentScore) {
        switch (currentScore) {
            case SCORE_ZERO:
                return SCORE_FIFTEEN;
            case SCORE_FIFTEEN:
                return SCORE_THIRTY;
            default:
                return SCORE_FORTY;
        }
    }

}

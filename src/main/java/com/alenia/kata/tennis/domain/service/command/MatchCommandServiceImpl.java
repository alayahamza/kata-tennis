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
        if (isGameOver(match)) {
            initNewGame(match);
        }
        Game lastGame = TennisUtil.getLastGame(match);
        addPoint(lastGame, 1);
        return matchRepository.save(match);
    }

    @Override
    public Match addPointToSecondPlayer(long matchId) throws TennisException {
        Match match = matchQueryService.findById(matchId);
        if (isGameOver(match)) {
            initNewGame(match);
        }
        Game lastGame = TennisUtil.getLastGame(match);
        addPoint(lastGame, 2);
        return matchRepository.save(match);
    }

    @Override
    public void initNewGame(Match match) {
        Set lastSet = TennisUtil.getLastSet(match);
        Game game = Game.builder()
                .firstPlayerScore(SCORE_ZERO)
                .secondPlayerScore(SCORE_ZERO)
                .number(lastSet.getGames().size() + 1)
                .build();
        lastSet.getGames().add(game);
    }

    @Override
    public boolean isGameOver(Match match) {
        return TennisUtil.getLastGame(match).getComment() != null;
    }

    @Override
    public void addPoint(Game lastGame, int player) {
        if (player == 1) {
            String score = calculateScore(lastGame, 1);
            switch (score) {
                case ADV:
                    lastGame.setSecondPlayerScore(SCORE_FORTY);
                    break;
                case DEUCE:
                    lastGame.setSecondPlayerScore(score);
                    break;
                case SCORE_ZERO:
                    lastGame.setSecondPlayerScore(score);
                    lastGame.setComment(FIRST_PLAYER_GAME);
            }
            lastGame.setFirstPlayerScore(score);
        } else {
            String score = calculateScore(lastGame, 2);
            switch (score) {
                case ADV:
                    lastGame.setFirstPlayerScore(SCORE_FORTY);
                    break;
                case DEUCE:
                    lastGame.setFirstPlayerScore(score);
                    break;
                case SCORE_ZERO:
                    lastGame.setFirstPlayerScore(score);
                    lastGame.setComment(SECOND_PLAYER_GAME);
            }
            lastGame.setSecondPlayerScore(score);
        }
    }

    @Override
    public String calculateScore(Game lastGame, int player) {
        String score = player == 1 ? lastGame.getFirstPlayerScore() : lastGame.getSecondPlayerScore();
        switch (score) {
            case SCORE_ZERO:
                return SCORE_FIFTEEN;
            case SCORE_FIFTEEN:
                return SCORE_THIRTY;
            case SCORE_THIRTY:
                return SCORE_FORTY;
            case DEUCE:
                return ADV;
            default:
                return checkDeuce(lastGame, player);
        }
    }

    @Override
    public String checkDeuce(Game lastGame, int player) {
        if (SCORE_FORTY.equals(lastGame.getFirstPlayerScore()) && SCORE_FORTY.equals(lastGame.getSecondPlayerScore())) {
            return ADV;
        } else if ((player == 1 && ADV.equals(lastGame.getSecondPlayerScore())) ||
                (player == 2 && ADV.equals(lastGame.getFirstPlayerScore()))) {
            return DEUCE;
        } else {
            return SCORE_ZERO;
        }
    }

}

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
                .firstPlayerSets(0)
                .secondPlayerSets(0)
                .comment(MATCH_STARTED)
                .build();
        return matchRepository.save(match);
    }

    @Override
    public Match addPointToFirstPlayer(long matchId) throws TennisException {
        Match match = matchQueryService.findById(matchId);
        checkScore(match, 1);
        addPoint(match, 1);
        return matchRepository.save(match);
    }

    @Override
    public void checkScore(Match match, int player) throws TennisException {
        if (isMatchOver(match)) {
            throw new TennisException(MATCH_FINISHED);
        }
        if (isSetOver(match)) {
            initNewSet(match);
            initNewGame(match);
        } else if (isGameOver(match)) {
            initNewGame(match);
        }
    }

    @Override
    public boolean isSetOver(Match match) {
        return TennisUtil.getLastSet(match).getComment() != null;
    }

    @Override
    public Match addPointToSecondPlayer(long matchId) throws TennisException {
        Match match = matchQueryService.findById(matchId);
        checkScore(match, 2);
        addPoint(match, 2);
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
    public void addPoint(Match match, int player) {
        Game lastGame = TennisUtil.getLastGame(match);
        Set lastSet = TennisUtil.getLastSet(match);
        if (player == 1) {
            updateFirstPlayerScore(lastGame, lastSet);
        } else {
            updateSecondPlayerScore(lastGame, lastSet);
        }
        checkSetWinner(match, player);

    }

    @Override
    public void updateSecondPlayerScore(Game lastGame, Set lastSet) {
        String score;
        score = calculateScore(lastGame, 2);
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
                lastSet.setSecondPlayerGames(lastSet.getSecondPlayerGames() + 1);
        }
        lastGame.setSecondPlayerScore(score);
    }

    @Override
    public void updateFirstPlayerScore(Game lastGame, Set lastSet) {
        String score;
        score = calculateScore(lastGame, 1);
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
                lastSet.setFirstPlayerGames(lastSet.getFirstPlayerGames() + 1);
        }
        lastGame.setFirstPlayerScore(score);
    }

    @Override
    public void checkSetWinner(Match match, int player) {
        if (player == 1) {
            checkSetForFirstPlayer(match);
        } else {
            checkSetForSecondPlayer(match);
        }
    }

    @Override
    public void checkSetForFirstPlayer(Match match) {
        Set lastSet = TennisUtil.getLastSet(match);
        if ((lastSet.getFirstPlayerGames() == 6 && lastSet.getSecondPlayerGames() <= 4)
                || lastSet.getFirstPlayerGames() == DEFAULT_GAMES_COUNT) {
            lastSet.setComment(FIRST_PLAYER_SET);
        }
        if (isSetOver(match)) {
            match.setFirstPlayerSets(match.getFirstPlayerSets() + 1);
            if (match.getFirstPlayerSets() == 2) {
                match.setComment(FIRST_PLAYER_MATCH);
            }
        }
    }

    @Override
    public void checkSetForSecondPlayer(Match match) {
        Set lastSet = TennisUtil.getLastSet(match);
        if ((lastSet.getSecondPlayerGames() == 6 && lastSet.getFirstPlayerGames() <= 4)
                || lastSet.getSecondPlayerGames() == DEFAULT_GAMES_COUNT) {
            lastSet.setComment(SECOND_PLAYER_SET);
        }
        if (isSetOver(match)) {
            match.setSecondPlayerSets(match.getSecondPlayerSets() + 1);
            if (match.getSecondPlayerSets() == 2) {
                match.setComment(SECOND_PLAYER_MATCH);
            }
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

    @Override
    public void initNewSet(Match match) {
        Set set = Set.builder()
                .number(match.getSets().size() + 1)
                .firstPlayerGames(0)
                .secondPlayerGames(0)
                .games(new ArrayList<>())
                .build();
        match.getSets().add(set);
    }

    @Override
    public boolean isMatchOver(Match match) {
        return FIRST_PLAYER_MATCH.equals(match.getComment()) || SECOND_PLAYER_MATCH.equals(match.getComment());
    }

}

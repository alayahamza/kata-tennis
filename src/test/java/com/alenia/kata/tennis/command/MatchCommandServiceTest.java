package com.alenia.kata.tennis.command;

import com.alenia.kata.tennis.domain.entity.Game;
import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.entity.Set;
import com.alenia.kata.tennis.domain.exception.TennisException;
import com.alenia.kata.tennis.domain.repository.GameRepository;
import com.alenia.kata.tennis.domain.repository.MatchRepository;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import com.alenia.kata.tennis.domain.repository.SetRepository;
import com.alenia.kata.tennis.domain.service.command.MatchCommandService;
import com.alenia.kata.tennis.domain.service.command.MatchCommandServiceImpl;
import com.alenia.kata.tennis.domain.service.query.MatchQueryServiceImpl;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryServiceImpl;
import com.alenia.kata.tennis.domain.utils.TennisUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.alenia.kata.tennis.domain.constant.TennisConstants.*;

@RunWith(SpringRunner.class)
@Import({MatchCommandServiceImpl.class, MatchQueryServiceImpl.class, PlayerQueryServiceImpl.class})
public class MatchCommandServiceTest {

    @Autowired
    private MatchCommandService matchCommandService;

    @MockBean
    private MatchRepository matchRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private SetRepository setRepository;

    private String firstPlayerName;
    private Player firstPlayer;

    private String secondPlayerName;
    private Player secondPlayer;

    private Match match;
    private Set set;
    private Game game;

    private List<Set> sets;
    private List<Game> games;
    private List<Match> matches;

    @Before
    public void setUp() {
        firstPlayerName = "John Doe 1";
        firstPlayer = Player.builder()
                .id(1)
                .name(firstPlayerName)
                .build();

        secondPlayerName = "John Doe 2";
        secondPlayer = Player.builder()
                .id(2)
                .name(secondPlayerName)
                .build();

        games = new ArrayList<>();
        sets = new ArrayList<>();

        game = Game.builder()
                .firstPlayerScore(SCORE_ZERO)
                .secondPlayerScore(SCORE_ZERO)
                .build();
        games.add(game);

        set = Set.builder().games(games).build();
        sets.add(set);
        match = Match.builder()
                .id(1)
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .sets(sets)
                .build();

        matches = new ArrayList<>();
        matches.add(match);
    }

    @Test
    public void should_return_match_when_create_with_firstPlayer_x_and_secondPlayer_y() throws TennisException {
        Mockito.when(playerRepository.findById(firstPlayer.getId())).thenReturn(Optional.ofNullable(firstPlayer));
        Mockito.when(playerRepository.findById(secondPlayer.getId())).thenReturn(Optional.ofNullable(secondPlayer));
        Mockito.when(matchRepository.save(Mockito.any())).thenReturn(match);
        Match match = matchCommandService.create(1, 2);
        Assert.assertNotNull(match);
        Assert.assertNotNull(match.getFirstPlayer());
        Assert.assertNotNull(match.getSecondPlayer());
        Assert.assertEquals(firstPlayer.getId(), match.getFirstPlayer().getId());
        Assert.assertEquals(secondPlayer.getId(), match.getSecondPlayer().getId());
        Assert.assertEquals(1, match.getSets().size());
        Assert.assertEquals(1, match.getSets().get(0).getGames().size());
        Assert.assertEquals(SCORE_ZERO, match.getSets().get(0).getGames().get(0).getFirstPlayerScore());
        Assert.assertEquals(SCORE_ZERO, match.getSets().get(0).getGames().get(0).getSecondPlayerScore());
        Assert.assertEquals(0, match.getFirstPlayerSets());
        Assert.assertEquals(0, match.getSecondPlayerSets());
    }

    @Test
    public void should_return_FIFTEEN_when_addPoint() {
        Game lastGame = TennisUtil.getLastGame(match);
        lastGame.setFirstPlayerScore(SCORE_ZERO);
        lastGame.setSecondPlayerScore(SCORE_FIFTEEN);
        matchCommandService.addPoint(match, 1);
        Assert.assertEquals(SCORE_FIFTEEN, game.getFirstPlayerScore());
    }

    @Test
    public void should_add_point_when_first_player_wins_a_point() throws TennisException {
        Mockito.when(matchRepository.findById(1)).thenReturn(Optional.ofNullable(match));
        Mockito.when(matchRepository.save(Mockito.any())).thenReturn(match);
        Match currentMatch = matchCommandService.addPointToFirstPlayer(1);
        Assert.assertEquals(SCORE_FIFTEEN, TennisUtil.getLastGame(currentMatch).getFirstPlayerScore());
        TennisUtil.getLastGame(currentMatch).setFirstPlayerScore(SCORE_THIRTY);
        currentMatch = matchCommandService.addPointToFirstPlayer(1);
        Assert.assertEquals(SCORE_FORTY, TennisUtil.getLastGame(currentMatch).getFirstPlayerScore());
    }

    @Test
    public void should_add_point_when_second_player_wins_a_point() throws TennisException {
        Mockito.when(matchRepository.findById(1)).thenReturn(Optional.ofNullable(match));
        Mockito.when(matchRepository.save(Mockito.any())).thenReturn(match);
        Match currentMatch = matchCommandService.addPointToSecondPlayer(1);
        Assert.assertEquals(SCORE_FIFTEEN, TennisUtil.getLastGame(currentMatch).getSecondPlayerScore());
        TennisUtil.getLastGame(currentMatch).setSecondPlayerScore(SCORE_THIRTY);
        currentMatch = matchCommandService.addPointToSecondPlayer(1);
        Assert.assertEquals(SCORE_FORTY, TennisUtil.getLastGame(currentMatch).getSecondPlayerScore());
    }

    @Test
    public void should_return_ADV_when_addPoint() {
        Game lastGame = TennisUtil.getLastGame(match);
        lastGame.setFirstPlayerScore(SCORE_FORTY);
        lastGame.setSecondPlayerScore(SCORE_FORTY);
        matchCommandService.addPoint(match, 1);
        Assert.assertEquals(ADV, game.getFirstPlayerScore());
    }

    @Test
    public void should_return_DEUCE_when_addPoint() {
        Game lastGame = TennisUtil.getLastGame(match);
        lastGame.setFirstPlayerScore(SCORE_FORTY);
        lastGame.setSecondPlayerScore(ADV);
        matchCommandService.addPoint(match, 1);
        Assert.assertEquals(DEUCE, game.getFirstPlayerScore());
        Assert.assertEquals(DEUCE, game.getSecondPlayerScore());
    }

    @Test
    public void should_return_true_when_isGameOver() {
        TennisUtil.getLastGame(match).setComment(FIRST_PLAYER_GAME);
        Assert.assertTrue(matchCommandService.isGameOver(match));
    }

    @Test
    public void should_add_a_game_to_match_when_initNewGame() {
        matchCommandService.initNewGame(match);
        Assert.assertEquals(2, TennisUtil.getLastSet(match).getGames().size());
    }

    @Test
    public void should_return_ADV_when_calculateScore() {
        game.setFirstPlayerScore(SCORE_FORTY);
        game.setSecondPlayerScore(SCORE_FORTY);
        Assert.assertEquals(ADV, matchCommandService.calculateScore(game, 1));
    }

    @Test
    public void should_return_DEUCE_when_checkDeuce() {
        game.setFirstPlayerScore(SCORE_FORTY);
        game.setSecondPlayerScore(ADV);
        Assert.assertEquals(DEUCE, matchCommandService.checkDeuce(game, 1));
    }

    @Test
    public void should_mark_set_as_over_when_checkSetWinner() {
        Set lastSet = TennisUtil.getLastSet(match);
        lastSet.setFirstPlayerGames(6);
        lastSet.setSecondPlayerGames(2);
        matchCommandService.checkSetWinner(match, 1);
        Assert.assertEquals(FIRST_PLAYER_SET, lastSet.getComment());
    }

    @Test
    public void should_win_set_when_addPoint() {
        Set lastSet = TennisUtil.getLastSet(match);
        lastSet.getGames().
                addAll(IntStream.rangeClosed(0, 5)
                        .mapToObj(i -> new Game())
                        .collect(Collectors.toList()));
        TennisUtil.getLastGame(match).setFirstPlayerScore(SCORE_FORTY);
        lastSet.setFirstPlayerGames(6);
        lastSet.setSecondPlayerGames(2);
        matchCommandService.addPoint(match, 1);
        Assert.assertEquals(FIRST_PLAYER_SET, lastSet.getComment());
    }

    @Test
    public void should_return_true_when_isSetOver() {
        Set lastSet = TennisUtil.getLastSet(match);
        lastSet.setComment(FIRST_PLAYER_SET);
        Assert.assertTrue(matchCommandService.isSetOver(match));
    }

    @Test
    public void should_create_a_new_set_when_initNewSet() {
        matchCommandService.initNewSet(match);
        Assert.assertEquals(2, match.getSets().size());
    }

    @Test(expected = TennisException.class)
    public void should_throw_exception_when_addPoint_and_match_finished() throws TennisException {
        match.setComment(FIRST_PLAYER_MATCH);
        matchCommandService.addPointToFirstPlayer(1);
    }

    @Test
    public void should_return_true_when_isMatchOver() {
        match.setComment(FIRST_PLAYER_MATCH);
        Assert.assertTrue(matchCommandService.isMatchOver(match));
    }

    @Test
    public void first_player_should_be_the_winner_when_checkSetForFirstPlayer() {
        TennisUtil.getLastSet(match).setFirstPlayerGames(6);
        TennisUtil.getLastSet(match).setSecondPlayerGames(2);
        match.setFirstPlayerSets(1);
        matchCommandService.checkSetForFirstPlayer(match);
        Assert.assertEquals(FIRST_PLAYER_SET, TennisUtil.getLastSet(match).getComment());
        Assert.assertEquals(FIRST_PLAYER_MATCH, match.getComment());
    }

    @Test
    public void second_player_should_be_the_winner_when_checkSetForSecondPlayer() {
        TennisUtil.getLastSet(match).setFirstPlayerGames(2);
        TennisUtil.getLastSet(match).setSecondPlayerGames(6);
        match.setSecondPlayerSets(1);
        matchCommandService.checkSetForSecondPlayer(match);
        Assert.assertEquals(SECOND_PLAYER_SET, TennisUtil.getLastSet(match).getComment());
        Assert.assertEquals(SECOND_PLAYER_MATCH, match.getComment());
    }

    @Test
    public void should_mark_second_player_as_set_winner_when_updateSecondPlayerScore() {
        Game lastGame = TennisUtil.getLastGame(match);
        Set lastSet = TennisUtil.getLastSet(match);
        matchCommandService.updateSecondPlayerScore(lastGame, lastSet);
        Assert.assertEquals(SCORE_FIFTEEN, lastGame.getSecondPlayerScore());
    }

    @Test
    public void should_mark_second_player_as_set_winner_when_updateFirstPlayerScore() {
        Game lastGame = TennisUtil.getLastGame(match);
        Set lastSet = TennisUtil.getLastSet(match);
        matchCommandService.updateFirstPlayerScore(lastGame, lastSet);
        Assert.assertEquals(SCORE_FIFTEEN, lastGame.getFirstPlayerScore());
    }

    @Test
    public void should_return_true_when_isTieScore() {
        Set lastSet = TennisUtil.getLastSet(match);
        match.setFirstPlayerSets(1);
        match.setSecondPlayerSets(1);
        lastSet.setFirstPlayerGames(6);
        lastSet.setSecondPlayerGames(6);
        Assert.assertTrue(matchCommandService.isTieScore(match));
    }

    @Test
    public void should_apply_tie_break_when_applyTieBreakRule() throws TennisException {
        Set lastSet = TennisUtil.getLastSet(match);
        match.setFirstPlayerSets(1);
        match.setSecondPlayerSets(1);
        lastSet.setFirstPlayerGames(9);
        lastSet.setSecondPlayerGames(7);
        matchCommandService.applyTieBreakRule(match, 1);
        Assert.assertEquals(FIRST_PLAYER_SET, lastSet.getComment());
        Assert.assertEquals(FIRST_PLAYER_MATCH, match.getComment());
    }

    @Test
    public void should_mark_first_player_as_winner_when_playerWinsSetAndMatch() {
        Set lastSet = TennisUtil.getLastSet(match);
        matchCommandService.playerWinsSetAndMatch(match, 1);
        Assert.assertEquals(FIRST_PLAYER_SET, lastSet.getComment());
        Assert.assertEquals(FIRST_PLAYER_MATCH, match.getComment());
    }
}

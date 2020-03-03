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
    }

    @Test
    public void should_return_FIFTEEN_when_addPoint() {
        Game game = Game.builder()
                .firstPlayerScore(SCORE_ZERO)
                .secondPlayerScore(SCORE_FIFTEEN)
                .build();
        matchCommandService.addPoint(game, 1);
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
        Game game = Game.builder()
                .firstPlayerScore(SCORE_FORTY)
                .secondPlayerScore(SCORE_FORTY)
                .build();
        matchCommandService.addPoint(game, 1);
        Assert.assertEquals(ADV, game.getFirstPlayerScore());
    }

    @Test
    public void should_return_DEUCE_when_addPoint() {
        Game game = Game.builder()
                .firstPlayerScore(SCORE_FORTY)
                .secondPlayerScore(ADV)
                .build();
        matchCommandService.addPoint(game, 1);
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
}

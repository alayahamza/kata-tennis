package com.alenia.kata.tennis.query;

import com.alenia.kata.tennis.entity.Game;
import com.alenia.kata.tennis.entity.Match;
import com.alenia.kata.tennis.entity.Player;
import com.alenia.kata.tennis.entity.Set;
import com.alenia.kata.tennis.exception.TennisException;
import com.alenia.kata.tennis.repository.GameRepository;
import com.alenia.kata.tennis.repository.MatchRepository;
import com.alenia.kata.tennis.repository.PlayerRepository;
import com.alenia.kata.tennis.repository.SetRepository;
import com.alenia.kata.tennis.service.query.MatchQueryService;
import com.alenia.kata.tennis.service.query.MatchQueryServiceImpl;
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

import static com.alenia.kata.tennis.constant.TennisConstants.SCORE_ZERO;

@RunWith(SpringRunner.class)
@Import({MatchQueryServiceImpl.class})
public class MatchQueryServiceTest {

    @Autowired
    private MatchQueryService matchQueryService;

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
    public void should_return_all_matches_when_findAll() {
        Mockito.when(matchRepository.findAll()).thenReturn(matches);
        List<Match> allMatches = matchQueryService.findAll();
        Assert.assertEquals(1, allMatches.size());
    }

    @Test
    public void should_return_match_with_id_1_when_findById() throws TennisException {
        Mockito.when(matchRepository.findById(1)).thenReturn(Optional.ofNullable(match));
        Match matchFound = matchQueryService.findById(1);
        Assert.assertEquals(match, matchFound);
    }

}

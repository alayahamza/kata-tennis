package com.alenia.kata.tennis.utils;

import com.alenia.kata.tennis.entity.Game;
import com.alenia.kata.tennis.entity.Match;
import com.alenia.kata.tennis.entity.Set;
import com.alenia.kata.tennis.utils.TennisUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.alenia.kata.tennis.constant.TennisConstants.SCORE_ZERO;

public class TennisUtilTest {

    private Match match;
    private Set set;
    private Game game;

    private List<Set> sets;
    private List<Game> games;
    private List<Match> matches;

    @Before
    public void setUp() {
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
                .sets(sets)
                .build();

        matches = new ArrayList<>();
        matches.add(match);
    }

    @Test
    public void should_return_last_set_when_getLastSet() {
        Set lastSet = TennisUtil.getLastSet(match);
        Assert.assertEquals(set, lastSet);
    }

    @Test
    public void should_return_last_game_when_getLastGame() {
        Game lastGame = TennisUtil.getLastGame(match);
        Assert.assertEquals(game, lastGame);
    }
}

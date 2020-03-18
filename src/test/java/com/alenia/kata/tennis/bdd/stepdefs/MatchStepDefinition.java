package com.alenia.kata.tennis.bdd.stepdefs;

import com.alenia.kata.tennis.api.to.PlayerTO;
import com.alenia.kata.tennis.domain.entity.Game;
import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.entity.Set;
import com.alenia.kata.tennis.domain.exception.TennisException;
import com.alenia.kata.tennis.domain.repository.MatchRepository;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import com.alenia.kata.tennis.domain.service.command.MatchCommandService;
import com.alenia.kata.tennis.domain.utils.TennisUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MatchStepDefinition extends BaseStepDefinition {

    @Autowired
    private MatchCommandService matchCommandService;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MatchRepository matchRepository;

    private Match match;
    private List<Player> playerList;

    @Given("the following players")
    public void theFollowingPlayers(List<PlayerTO> players) {
        playerList = playerRepository.saveAll(players.stream()
                .map(playerTO -> Player.builder()
                        .id(playerTO.getId())
                        .name(playerTO.getName())
                        .build())
                .collect(Collectors.toList()));
    }

    @When("create a match")
    public void createAMatch() throws TennisException {
        match = matchCommandService.create(playerList.get(0).getId(), playerList.get(1).getId());
    }

    @Then("match status is {string}")
    public void matchStarted(String comment) {
        assertEquals(comment, match.getComment());
    }

    @And("both players have {int} set score")
    public void bothPlayersHaveSetScore(int score) {
        assertEquals(score, match.getFirstPlayerSets());
        assertEquals(score, match.getSecondPlayerSets());
    }

    @And("last set has {int} score for both players")
    public void lastSetHasScoreForBothPlayers(int score) {
        assertEquals(score, TennisUtil.getLastSet(match).getFirstPlayerGames());
        assertEquals(score, TennisUtil.getLastSet(match).getSecondPlayerGames());
    }

    @And("last game has {string} score for both players")
    public void lastGameHasScoreForBothPlayers(String score) {
        assertEquals(score, TennisUtil.getLastGame(match).getFirstPlayerScore());
        assertEquals(score, TennisUtil.getLastGame(match).getSecondPlayerScore());
    }

    @And("the following match")
    public void theFollowingMatch(List<Match> matches) {
        Match match = matches.get(0);
        Set set = new Set();
        Game game = new Game();
        set.setGames(Arrays.asList(game));
        match.setSets(Arrays.asList(set));
        this.match = matchRepository.save(match);
    }

    @And("last game score")
    public void lastGameScore(Game game) {
        TennisUtil.getLastGame(match).setFirstPlayerScore(game.getFirstPlayerScore());
        TennisUtil.getLastGame(match).setSecondPlayerScore(game.getSecondPlayerScore());
        match = matchRepository.save(match);
    }

    @When("first player scores a point")
    public void firstPlayerScoresAPoint() throws TennisException {
        match = matchCommandService.addPointToFirstPlayer(match.getId());
    }

    @Then("last game score becomes")
    public void lastGameScoreBecomes(Game game) {
        assertEquals(game.getFirstPlayerScore(), TennisUtil.getLastGame(match).getFirstPlayerScore());
        assertEquals(game.getSecondPlayerScore(), TennisUtil.getLastGame(match).getSecondPlayerScore());
    }
}

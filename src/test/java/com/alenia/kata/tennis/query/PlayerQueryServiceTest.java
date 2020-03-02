package com.alenia.kata.tennis.query;

import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.exception.TennisException;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryService;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryServiceImpl;
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

@RunWith(SpringRunner.class)
@Import(PlayerQueryServiceImpl.class)
public class PlayerQueryServiceTest {

    @Autowired
    private PlayerQueryService playerQueryService;

    @MockBean
    private PlayerRepository playerRepository;

    private String firstPlayerName;
    private Player firstPlayer;
    private List<Player> players;

    @Before
    public void setUp() {
        firstPlayerName = "John Doe 1";
        firstPlayer = Player.builder()
                .id(1)
                .name(firstPlayerName)
                .build();

        players = new ArrayList<>();
        players.add(firstPlayer);
    }

    @Test
    public void should_find_player_with_id_x_when_findById_x() throws TennisException {
        Mockito.when(playerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(firstPlayer));
        Player player = playerQueryService.findById(firstPlayer.getId());
        Assert.assertNotNull(player);
        Assert.assertEquals(firstPlayer.getId(), player.getId());
    }

    @Test
    public void should_return_players_when_findAll() {
        Mockito.when(playerRepository.findAll()).thenReturn(players);
        List<Player> allPlayers = playerQueryService.findAll();
        Assert.assertEquals(1, allPlayers.size());
    }
}

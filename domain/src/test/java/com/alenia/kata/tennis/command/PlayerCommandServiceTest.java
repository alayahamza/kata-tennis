package com.alenia.kata.tennis.command;

import com.alenia.kata.tennis.entity.Player;
import com.alenia.kata.tennis.repository.PlayerRepository;
import com.alenia.kata.tennis.service.command.PlayerCommandService;
import com.alenia.kata.tennis.service.command.PlayerCommandServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(PlayerCommandServiceImpl.class)
public class PlayerCommandServiceTest {

    @Autowired
    private PlayerCommandService playerCommandService;

    @MockBean
    private PlayerRepository playerRepository;

    private String firstPlayerName;
    private Player firstPlayer;

    @Before
    public void setUp() {
        firstPlayerName = "John Doe 1";
        firstPlayer = Player.builder()
                .id(1)
                .name(firstPlayerName)
                .build();

    }

    @Test
    public void should_create_player_when_create() {
        Mockito.when(playerRepository.save(Mockito.any())).thenReturn(firstPlayer);
        Player player = playerCommandService.create(firstPlayerName);
        Assert.assertNotNull(player);
        Assert.assertEquals(firstPlayerName, player.getName());
    }

}

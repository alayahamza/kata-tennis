package com.alenia.kata.tennis.api.controller;

import com.alenia.kata.tennis.api.controller.query.PlayerQueryController;
import com.alenia.kata.tennis.api.mapper.PlayerMapperImpl;
import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerQueryController.class)
@Import({PlayerMapperImpl.class})
public class PlayerQueryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayerQueryService playerQueryService;

    @Test
    public void should_return_all_players() throws Exception {
        Player player = new Player(1, "Player");
        List<Player> players = Arrays.asList(player);
        given(playerQueryService.findAll()).willReturn(players);

        mvc.perform(get("/api/v1/players/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo(player.getName())));
    }
}

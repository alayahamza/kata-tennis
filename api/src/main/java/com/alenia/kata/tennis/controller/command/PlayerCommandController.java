package com.alenia.kata.tennis.controller.command;

import com.alenia.kata.tennis.mapper.PlayerMapper;
import com.alenia.kata.tennis.to.PlayerTO;
import com.alenia.kata.tennis.service.command.PlayerCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/players")
public class PlayerCommandController {

    private final PlayerCommandService playerCommandService;
    private final PlayerMapper playerMapper;

    public PlayerCommandController(PlayerCommandService playerCommandService, PlayerMapper playerMapper) {
        this.playerCommandService = playerCommandService;
        this.playerMapper = playerMapper;
    }

    @PostMapping
    public ResponseEntity<PlayerTO> create(@RequestBody PlayerTO player) {
        return ResponseEntity.ok().body(playerMapper.toPlayerTO(playerCommandService.create(player.getName())));
    }

}

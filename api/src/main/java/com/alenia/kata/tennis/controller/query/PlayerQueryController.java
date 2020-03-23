package com.alenia.kata.tennis.controller.query;

import com.alenia.kata.tennis.mapper.PlayerMapper;
import com.alenia.kata.tennis.to.PlayerTO;
import com.alenia.kata.tennis.exception.TennisException;
import com.alenia.kata.tennis.service.query.PlayerQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/players")
public class PlayerQueryController {

    private final PlayerQueryService playerQueryService;
    private final PlayerMapper playerMapper;

    public PlayerQueryController(PlayerQueryService playerQueryService, PlayerMapper playerMapper) {
        this.playerQueryService = playerQueryService;
        this.playerMapper = playerMapper;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<PlayerTO> findById(@PathVariable long id) throws TennisException {
        return ResponseEntity.ok().body(playerMapper.toPlayerTO(playerQueryService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<PlayerTO>> findAll() {
        return ResponseEntity.ok().body(playerMapper.toPlayerTOList(playerQueryService.findAll()));
    }

}

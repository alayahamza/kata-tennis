package com.alenia.kata.tennis.controller.command;

import com.alenia.kata.tennis.mapper.MatchMapper;
import com.alenia.kata.tennis.to.MatchTO;
import com.alenia.kata.tennis.exception.TennisException;
import com.alenia.kata.tennis.service.command.MatchCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/matches")
@Slf4j
public class MatchCommandController {

    private final MatchCommandService matchCommandService;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchCommandController(MatchCommandService matchCommandService, MatchMapper matchMapper) {
        this.matchCommandService = matchCommandService;
        this.matchMapper = matchMapper;
    }

    @PostMapping(value = "firstPlayer/{firstPlayerId}/secondPlayer/{secondPlayerId}")
    public ResponseEntity<MatchTO> create(@PathVariable long firstPlayerId, @PathVariable long secondPlayerId)
            throws TennisException {
        log.info("Creating match with first player id : " + firstPlayerId + " and second player id : " + secondPlayerId);
        return ResponseEntity.ok()
                .body(matchMapper.toMatchTO(matchCommandService.create(firstPlayerId, secondPlayerId)));
    }

    @PostMapping(value = "{matchId}/firstPlayer/score-point")
    public ResponseEntity<MatchTO> scorePointFirstPlayer(@PathVariable long matchId)
            throws TennisException {
        return ResponseEntity.ok()
                .body(matchMapper.toMatchTO(matchCommandService.addPointToFirstPlayer(matchId)));
    }

    @PostMapping(value = "{matchId}/secondPlayer/score-point")
    public ResponseEntity<MatchTO> scorePointSecondPlayer(@PathVariable long matchId)
            throws TennisException {
        return ResponseEntity.ok()
                .body(matchMapper.toMatchTO(matchCommandService.addPointToSecondPlayer(matchId)));
    }
}

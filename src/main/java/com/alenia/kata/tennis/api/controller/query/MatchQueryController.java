package com.alenia.kata.tennis.api.controller.query;

import com.alenia.kata.tennis.api.mapper.MatchMapper;
import com.alenia.kata.tennis.api.to.MatchTO;
import com.alenia.kata.tennis.domain.service.query.MatchQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/matches")
public class MatchQueryController {

    private final MatchQueryService matchQueryService;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchQueryController(MatchQueryService matchQueryService, MatchMapper matchMapper) {
        this.matchQueryService = matchQueryService;
        this.matchMapper = matchMapper;
    }

    @GetMapping
    public ResponseEntity<List<MatchTO>> findAll() {
        return ResponseEntity.ok()
                .body(matchMapper.toMatchTOList(matchQueryService.findAll()));
    }

}

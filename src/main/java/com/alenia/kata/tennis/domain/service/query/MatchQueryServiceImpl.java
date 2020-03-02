package com.alenia.kata.tennis.domain.service.query;

import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.exception.TennisException;
import com.alenia.kata.tennis.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchQueryServiceImpl implements MatchQueryService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchQueryServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match findById(long matchId) throws TennisException {
        return matchRepository.findById(matchId).orElseThrow(() -> new TennisException("Match not Found with id :" + matchId));
    }
}

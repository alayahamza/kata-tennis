package com.alenia.kata.tennis.infra.repository;

import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class H2DBMatchRepository implements MatchRepository {

    private SpringDataMatchRepository springDataMatchRepository;

    @Autowired
    public H2DBMatchRepository(SpringDataMatchRepository springDataMatchRepository) {
        this.springDataMatchRepository = springDataMatchRepository;
    }

    @Override
    public Match save(Match match) {
        return springDataMatchRepository.save(match);
    }

    @Override
    public Optional<Match> findById(long id) {
        return springDataMatchRepository.findById(id);
    }

    @Override
    public List<Match> findAll() {
        return springDataMatchRepository.findAll();
    }
}

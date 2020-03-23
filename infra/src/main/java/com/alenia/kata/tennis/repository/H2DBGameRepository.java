package com.alenia.kata.tennis.repository;

import com.alenia.kata.tennis.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class H2DBGameRepository implements GameRepository {

    private SpringDataGameRepository springDataGameRepository;

    @Autowired
    public H2DBGameRepository(SpringDataGameRepository springDataGameRepository) {
        this.springDataGameRepository = springDataGameRepository;
    }

    @Override
    public Game save(Game game) {
        return springDataGameRepository.save(game);
    }
}

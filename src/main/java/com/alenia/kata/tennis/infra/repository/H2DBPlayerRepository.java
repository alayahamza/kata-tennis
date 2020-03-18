package com.alenia.kata.tennis.infra.repository;

import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class H2DBPlayerRepository implements PlayerRepository {

    private SpringDataPlayerRepository springDataPlayerRepository;

    @Autowired
    public H2DBPlayerRepository(SpringDataPlayerRepository springDataPlayerRepository) {
        this.springDataPlayerRepository = springDataPlayerRepository;
    }

    @Override
    public Player save(Player player) {
        return springDataPlayerRepository.save(player);
    }

    @Override
    public Optional<Player> findById(long id) {
        return springDataPlayerRepository.findById(id);
    }

    @Override
    public List<Player> findAll() {
        return springDataPlayerRepository.findAll();
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        return springDataPlayerRepository.saveAll(players);
    }
}

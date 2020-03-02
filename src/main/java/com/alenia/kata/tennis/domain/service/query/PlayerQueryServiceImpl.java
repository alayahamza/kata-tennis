package com.alenia.kata.tennis.domain.service.query;

import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.exception.TennisException;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerQueryServiceImpl implements PlayerQueryService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerQueryServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findById(long id) throws TennisException {
        return playerRepository.findById(id).orElseThrow(() -> new TennisException("Player not found with id : " + id));
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }
}

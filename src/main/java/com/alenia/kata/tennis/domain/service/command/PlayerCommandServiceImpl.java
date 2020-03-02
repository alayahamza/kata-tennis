package com.alenia.kata.tennis.domain.service.command;

import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerCommandServiceImpl implements PlayerCommandService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerCommandServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player create(String playerName) {
        return playerRepository.save(Player.builder()
                .name(playerName)
                .build());
    }
}

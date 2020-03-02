package com.alenia.kata.tennis.domain.repository;

import com.alenia.kata.tennis.domain.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player);

    Optional<Player> findById(long id);

    List<Player> findAll();
}

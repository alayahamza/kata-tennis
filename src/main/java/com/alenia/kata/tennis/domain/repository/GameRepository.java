package com.alenia.kata.tennis.domain.repository;

import com.alenia.kata.tennis.domain.entity.Game;

public interface GameRepository {

    Game save(Game game);
}

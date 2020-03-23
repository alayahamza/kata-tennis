package com.alenia.kata.tennis.repository;

import com.alenia.kata.tennis.entity.Game;

public interface GameRepository {

    Game save(Game game);
}

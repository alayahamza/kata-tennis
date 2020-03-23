package com.alenia.kata.tennis.service.query;

import com.alenia.kata.tennis.entity.Player;
import com.alenia.kata.tennis.exception.TennisException;

import java.util.List;

public interface PlayerQueryService {
    Player findById(long id) throws TennisException;

    List<Player> findAll();
}

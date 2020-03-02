package com.alenia.kata.tennis.domain.service.query;

import com.alenia.kata.tennis.domain.entity.Player;
import com.alenia.kata.tennis.domain.exception.TennisException;

import java.util.List;

public interface PlayerQueryService {
    Player findById(long id) throws TennisException;

    List<Player> findAll();
}

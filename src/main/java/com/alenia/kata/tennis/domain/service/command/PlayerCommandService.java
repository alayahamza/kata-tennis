package com.alenia.kata.tennis.domain.service.command;

import com.alenia.kata.tennis.domain.entity.Player;

public interface PlayerCommandService {
    Player create(String playerName);
}

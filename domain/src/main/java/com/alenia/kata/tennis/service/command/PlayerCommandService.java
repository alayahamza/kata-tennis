package com.alenia.kata.tennis.service.command;

import com.alenia.kata.tennis.entity.Player;

public interface PlayerCommandService {
    Player create(String playerName);
}

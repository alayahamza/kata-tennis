package com.alenia.kata.tennis.domain.utils;

import com.alenia.kata.tennis.domain.entity.Game;
import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.entity.Set;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TennisUtil {
    public static Set getLastSet(Match match) {
        return match.getSets().get(match.getSets().size() - 1);
    }

    public Game getLastGame(Match match) {
        List<Game> games = getLastSet(match).getGames();
        return games.get(games.size() - 1);
    }
}

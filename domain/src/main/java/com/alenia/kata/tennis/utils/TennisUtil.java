package com.alenia.kata.tennis.utils;

import com.alenia.kata.tennis.entity.Game;
import com.alenia.kata.tennis.entity.Match;
import com.alenia.kata.tennis.entity.Set;
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

package com.alenia.kata.tennis.domain.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TennisConstants {

    public static final int SCORE_ZERO = 0;
    public static final int SCORE_FIFTEEN = 15;
    public static final int SCORE_THIRTY = 30;
    public static final int SCORE_FORTY = 40;

    public static final String MATCH_STARTED = "Match started";
    public static final String FIRST_PLAYER_GAME = "First player won the game";
    public static final String SECOND_PLAYER_GAME = "Second player won the game";
    public static final String GAME_WON_ERROR = "The Game already has a winner";
}

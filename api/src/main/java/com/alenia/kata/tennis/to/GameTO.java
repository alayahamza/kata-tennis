package com.alenia.kata.tennis.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTO {

    private int number;

    private String firstPlayerScore;

    private String secondPlayerScore;

    private String comment;
}

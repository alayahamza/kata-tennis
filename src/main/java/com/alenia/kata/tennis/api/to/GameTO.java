package com.alenia.kata.tennis.api.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTO {

    private int number;

    private int firstPlayerScore;

    private int secondPlayerScore;

    private String comment;
}

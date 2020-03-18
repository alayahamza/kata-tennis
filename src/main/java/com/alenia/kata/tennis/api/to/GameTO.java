package com.alenia.kata.tennis.api.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameTO {

    private int number;

    private String firstPlayerScore;

    private String secondPlayerScore;

    private String comment;
}

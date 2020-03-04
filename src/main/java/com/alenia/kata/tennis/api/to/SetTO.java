package com.alenia.kata.tennis.api.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetTO {

    private int number;

    private List<GameTO> games;

    private int firstPlayerGames;

    private int secondPlayerGames;

    private String comment;
}

package com.alenia.kata.tennis.api.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchTO {

    private long id;

    private List<SetTO> sets;

    private PlayerTO firstPlayer;

    private PlayerTO secondPlayer;

    private int firstPlayerSets;

    private int secondPlayerSets;

    private String comment;
}

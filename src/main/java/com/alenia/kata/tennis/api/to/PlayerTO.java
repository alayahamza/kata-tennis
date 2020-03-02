package com.alenia.kata.tennis.api.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerTO {

    private long id;

    private String name;
}

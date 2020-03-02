package com.alenia.kata.tennis.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NUMBER")
    private int number;

    @Column(name = "FIRST_PLAYER_SCORE")
    private int firstPlayerScore;

    @Column(name = "SECOND_PLAYER_SCORE")
    private int secondPlayerScore;

    @Column(name = "COMMENT")
    private String comment;
}

package com.alenia.kata.tennis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Set {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NUMBER")
    private int number;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Game> games;

    @Column(name = "FIRST_PLAYER_GAMES")
    private int firstPlayerGames;

    @Column(name = "SECOND_PLAYER_GAMES")
    private int secondPlayerGames;

    @Column(name = "COMMENT")
    private String comment;
}

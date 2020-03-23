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
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "FIRST_PLAYER_SETS")
    private int firstPlayerSets;

    @Column(name = "SECOND_PLAYER_SETS")
    private int secondPlayerSets;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Set> sets;

    @OneToOne
    private Player firstPlayer;

    @OneToOne
    private Player secondPlayer;

}

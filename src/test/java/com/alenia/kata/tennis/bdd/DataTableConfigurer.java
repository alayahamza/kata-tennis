package com.alenia.kata.tennis.bdd;

import com.alenia.kata.tennis.api.to.PlayerTO;
import com.alenia.kata.tennis.domain.entity.Game;
import com.alenia.kata.tennis.domain.entity.Match;
import com.alenia.kata.tennis.domain.entity.Player;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Locale;

@Configurable
public class DataTableConfigurer implements TypeRegistryConfigurer {

    public Locale locale() {
        return Locale.ENGLISH;
    }

    public void configureTypeRegistry(TypeRegistry typeRegistry) {

        typeRegistry.defineDataTableType(
                new DataTableType(PlayerTO.class,
                        (TableEntryTransformer<PlayerTO>) entry ->
                                PlayerTO.builder()
                                        .id(0)
                                        .name(entry.get("name"))
                                        .build()));

        typeRegistry.defineDataTableType(
                new DataTableType(Match.class,
                        (TableEntryTransformer<Match>) entry ->
                                Match.builder().id(Long.parseLong(entry.get("id")))
                                        .comment(entry.get("comment"))
                                        .firstPlayer(Player.builder()
                                                .id(Long.parseLong(entry.get("first player id")))
                                                .name(entry.get("first player's name"))
                                                .build())
                                        .secondPlayer(Player.builder()
                                                .id(Long.parseLong(entry.get("second player id")))
                                                .name(entry.get("second player's name"))
                                                .build())
                                        .firstPlayerSets(Integer.parseInt(entry.get("first player sets")))
                                        .secondPlayerSets(Integer.parseInt(entry.get("second player sets")))
                                        .build()));

        typeRegistry.defineDataTableType(
                new DataTableType(Game.class,
                        (TableEntryTransformer<Game>) entry ->
                                Game.builder()
                                        .firstPlayerScore(entry.get("first player"))
                                        .secondPlayerScore(entry.get("second player"))
                                        .build()));

    }

}

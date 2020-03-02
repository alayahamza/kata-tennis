package com.alenia.kata.tennis.api.mapper;

import com.alenia.kata.tennis.api.to.PlayerTO;
import com.alenia.kata.tennis.domain.entity.Player;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerTO toPlayerTO(Player player);

    @IterableMapping(elementTargetType = PlayerTO.class)
    List<PlayerTO> toPlayerTOList(List<Player> players);
}

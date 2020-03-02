package com.alenia.kata.tennis.api.mapper;

import com.alenia.kata.tennis.api.to.MatchTO;
import com.alenia.kata.tennis.domain.entity.Match;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    MatchTO toMatchTO(Match match);

    @IterableMapping(elementTargetType = MatchTO.class)
    List<MatchTO> toMatchTOList(List<Match> matches);
}

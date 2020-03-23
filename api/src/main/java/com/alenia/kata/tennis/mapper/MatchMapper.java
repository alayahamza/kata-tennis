package com.alenia.kata.tennis.mapper;

import com.alenia.kata.tennis.to.MatchTO;
import com.alenia.kata.tennis.entity.Match;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    MatchTO toMatchTO(Match match);

    @IterableMapping(elementTargetType = MatchTO.class)
    List<MatchTO> toMatchTOList(List<Match> matches);
}

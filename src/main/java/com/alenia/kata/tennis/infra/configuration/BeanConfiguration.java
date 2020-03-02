package com.alenia.kata.tennis.infra.configuration;

import com.alenia.kata.tennis.domain.repository.MatchRepository;
import com.alenia.kata.tennis.domain.repository.PlayerRepository;
import com.alenia.kata.tennis.domain.service.command.MatchCommandService;
import com.alenia.kata.tennis.domain.service.command.MatchCommandServiceImpl;
import com.alenia.kata.tennis.domain.service.command.PlayerCommandService;
import com.alenia.kata.tennis.domain.service.command.PlayerCommandServiceImpl;
import com.alenia.kata.tennis.domain.service.query.MatchQueryService;
import com.alenia.kata.tennis.domain.service.query.MatchQueryServiceImpl;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryService;
import com.alenia.kata.tennis.domain.service.query.PlayerQueryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    MatchQueryService matchQueryService(MatchRepository matchRepository) {
        return new MatchQueryServiceImpl(matchRepository);
    }

    @Bean
    MatchCommandService matchCommandService(MatchQueryService matchQueryService, MatchRepository matchRepository,
                                            PlayerQueryService playerQueryService) {
        return new MatchCommandServiceImpl(matchQueryService, matchRepository, playerQueryService);
    }

    @Bean
    PlayerCommandService playerCommandService(PlayerRepository playerRepository) {
        return new PlayerCommandServiceImpl(playerRepository);
    }

    @Bean
    PlayerQueryService playerQueryService(PlayerRepository playerRepository) {
        return new PlayerQueryServiceImpl(playerRepository);
    }
}

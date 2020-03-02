package com.alenia.kata.tennis.infra.repository;

import com.alenia.kata.tennis.domain.entity.Set;
import com.alenia.kata.tennis.domain.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class H2DBSetRepository implements SetRepository {

    private SpringDataSetRepository springDataSetRepository;

    @Autowired
    public H2DBSetRepository(SpringDataSetRepository springDataSetRepository) {
        this.springDataSetRepository = springDataSetRepository;
    }

    @Override
    public Set save(Set set) {
        return springDataSetRepository.save(set);
    }
}

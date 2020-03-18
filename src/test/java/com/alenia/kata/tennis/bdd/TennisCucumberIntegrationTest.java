package com.alenia.kata.tennis.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
        plugin = {"pretty"},
        glue = "classpath:com.alenia.kata.tennis.bdd"
)
public class TennisCucumberIntegrationTest {
}

package com.springReactive.restClient.services;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Duration;

import javax.validation.constraints.AssertTrue;

/**
 * Created by mshaik on 1/15/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JokeServiceTest {


  @Autowired
  JokeService jokeService;


  @Test
  public void jokeSyncTest() {

    String joke = jokeService.getJokeSync("vali","Meera");
    assertTrue(joke.contains("vali") || joke.contains("Meera"));

  }

  @Test
  public void getJokeAsync() {
    String joke = jokeService.getJokeAsync("vali", "Meera")
        .doOnNext(System.out::println)
        .block(Duration.ofSeconds(2));
    assertTrue(joke.contains("vali") || joke.contains("Meera"));
  }


  @Test
  public void useStepVerifier() {
    StepVerifier.create(jokeService.getJokeAsync("vali", "Meera").doOnNext(System.out::println))
        .assertNext(joke -> assertTrue(joke.contains("vali") || joke.contains("Meera")))
        .verifyComplete();
  }

}

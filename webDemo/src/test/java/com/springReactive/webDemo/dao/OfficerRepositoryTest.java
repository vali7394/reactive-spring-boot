package com.springReactive.webDemo.dao;

import com.springReactive.webDemo.entities.Officer;
import com.springReactive.webDemo.entities.Rank;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mshaik on 1/15/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OfficerRepositoryTest {


  @Autowired
  OfficerRepository officerRepository;


  private List<Officer> officers = Arrays.asList(
      new Officer(Rank.CAPTAIN, "James", "Kirk"),
      new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
      new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
      new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
      new Officer(Rank.CAPTAIN, "Jonathan", "Archer"));


  @Before
  public void setUp() {

    officerRepository.deleteAll()
        .thenMany(Flux.fromIterable(officers))
        .flatMap(officerRepository::save)
        .then()
        .block();


  }


  @Test
  public void save() {
    Officer lorca = new Officer(Rank.CAPTAIN, "Gabriel", "Lorca");
    StepVerifier.create(officerRepository.save(lorca))
        .expectNextMatches(officer -> !officer.getId().equals(""))
        .verifyComplete();
  }

  @Test
  public void findAll() {
    StepVerifier.create(officerRepository.findAll())
        .expectNextCount(5)
        .verifyComplete();
  }

  @Test
  public void findById() {
    officers.stream()
        .map(Officer::getId)
        .forEach(id ->
            StepVerifier.create(officerRepository.findById(id))
                .expectNextCount(1)
                .verifyComplete());
  }

  @Test
  public void findByIdNotExist() {
    StepVerifier.create(officerRepository.findById("xyz"))
        .verifyComplete();
  }


  @Test
  public void count() {
    StepVerifier.create(officerRepository.count())
        .expectNext(5L)
        .verifyComplete();
  }


  @Test
  public void findByRank() {
    StepVerifier.create(
        officerRepository.findByRank(Rank.CAPTAIN)
            .map(Officer::getRank)
            .distinct())
        .expectNextCount(1)
        .verifyComplete();

    StepVerifier.create(
        officerRepository.findByRank(Rank.ENSIGN)
            .map(Officer::getRank)
            .distinct())
        .verifyComplete();
  }

  @Test
  public void findByLast() {
    officers.stream()
        .map(Officer::getLast)
        .forEach(lastName ->
            StepVerifier.create(officerRepository.findByLast(lastName))
                .expectNextMatches(officer ->
                    officer.getLast().equals(lastName))
                .verifyComplete());
  }

}

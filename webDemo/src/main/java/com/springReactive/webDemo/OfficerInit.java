package com.springReactive.webDemo;

import com.springReactive.webDemo.dao.OfficerRepository;
import com.springReactive.webDemo.entities.Officer;
import com.springReactive.webDemo.entities.Rank;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mshaik on 1/16/19.
 */
@AllArgsConstructor
public class OfficerInit implements ApplicationRunner {


  private OfficerRepository officerRepository;



  @Override
  public void run(ApplicationArguments applicationArguments) throws Exception {


     List<Officer> officers = Arrays.asList(
        new Officer(Rank.CAPTAIN, "James", "Kirk"),
        new Officer(Rank.CAPTAIN, "Jean-Luc", "Picard"),
        new Officer(Rank.CAPTAIN, "Benjamin", "Sisko"),
        new Officer(Rank.CAPTAIN, "Kathryn", "Janeway"),
        new Officer(Rank.CAPTAIN, "Jonathan", "Archer"));

    officerRepository.deleteAll()
        .thenMany(Flux.fromIterable(officers))
        .flatMap(officerRepository::save)
        .thenMany(officerRepository.findAll())
        .subscribe(System.out::println);


  }
}

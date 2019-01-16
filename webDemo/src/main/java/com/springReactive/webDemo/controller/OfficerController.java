package com.springReactive.webDemo.controller;

import com.springReactive.webDemo.dao.OfficerRepository;
import com.springReactive.webDemo.entities.Officer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by mshaik on 1/16/19.
 */
@RestController
@RequestMapping("/officers")
public class OfficerController {

  private OfficerRepository repository;

  public OfficerController(OfficerRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public Flux<Officer> getAllOfficers() {
    return repository.findAll();
  }

  @GetMapping("{id}")
  public Mono<Officer> getOfficer(@PathVariable String id) {
    return repository.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Officer> saveOfficer(@RequestBody Officer officer) {
    return repository.save(officer);
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<Officer>> updateOfficer(@PathVariable(value = "id") String id,
                                                     @RequestBody Officer officer) {
    return repository.findById(id)
        .flatMap(existingOfficer -> {
          existingOfficer.setRank(officer.getRank());
          existingOfficer.setFirst(officer.getFirst());
          existingOfficer.setLast(officer.getLast());
          return repository.save(existingOfficer);
        })
        .map(updateOfficer -> new ResponseEntity<>(updateOfficer, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<ResponseEntity<Void>> deleteOfficer(@PathVariable(value = "id") String id) {
    return repository.deleteById(id)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping
  public Mono<Void> deleteAllOfficers() {
    return repository.deleteAll();
  }

}

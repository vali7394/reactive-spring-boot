package com.springReactive.webDemo.controller;

import com.springReactive.webDemo.dao.OfficerRepository;
import com.springReactive.webDemo.entities.Officer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * Created by mshaik on 1/16/19.
 */
@Component
@AllArgsConstructor
public class OfficerHandler {

  private OfficerRepository repository;


  public Mono<ServerResponse> listOfficers(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(repository.findAll(), Officer.class);
  }

  public Mono<ServerResponse> createOfficer(ServerRequest request) {
    Mono<Officer> officerMono = request.bodyToMono(Officer.class);
    return officerMono.flatMap(officer ->
        ServerResponse.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.save(officer), Officer.class));
  }

  public Mono<ServerResponse> getOfficer(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    Mono<Officer> personMono = this.repository.findById(id);
    return personMono
        .flatMap(person -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromObject(person)))
        .switchIfEmpty(notFound);
  }


}

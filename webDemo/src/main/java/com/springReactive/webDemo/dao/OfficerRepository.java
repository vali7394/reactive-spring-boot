package com.springReactive.webDemo.dao;

import com.springReactive.webDemo.entities.Officer;
import com.springReactive.webDemo.entities.Rank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * Created by mshaik on 1/15/19.
 */
public interface OfficerRepository extends ReactiveMongoRepository<Officer,String> {

   Flux<Officer> findByRank(Rank rank);
   Flux<Officer> findByLast(String lastName);




}

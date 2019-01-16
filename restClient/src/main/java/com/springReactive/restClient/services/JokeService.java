package com.springReactive.restClient.services;

import com.springReactive.restClient.json.JokeRespone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


/**
 * Created by mshaik on 1/15/19.
 */
@Service
public class JokeService {

  private Logger logger = LoggerFactory.getLogger(JokeService.class);

  private static final String URL = "http://api.icndb.com/jokes/random?limitTo=[nerdy]";

  private WebClient client = WebClient.create("http://api.icndb.com");

  private RestTemplate restTemplate;

  @Autowired
  JokeService(RestTemplateBuilder restTemplateBuilder) {
    restTemplate =restTemplateBuilder.build();
  }



  public String getJokeSync(String first, String last) {

    String url = String.format("%s&firstName=%s&lastName=%s", URL, first, last);

    //Sync call get Json and convert to Obj
    JokeRespone response = restTemplate.getForObject(url, JokeRespone.class);
    String joke = response.getValue().getJoke();
    logger.info(joke);
    return joke;
  }


  public Mono<String> getJokeAsync(String first , String last) {

    String path = "/jokes/random?limitTo=[nerdy]&firstName={first}&lastName={last}";
    return client.get()
        .uri(path, first, last)
        .retrieve()
        .bodyToMono(JokeRespone.class)
        .map(jokeRespone -> jokeRespone.getValue().getJoke());

  }

}

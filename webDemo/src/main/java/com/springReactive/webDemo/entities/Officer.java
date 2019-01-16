package com.springReactive.webDemo.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mshaik on 1/15/19.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document
public class Officer {


  @Id
  private String id;
  private Rank rank;
  private String first;
  private String last;


  public Officer(Rank rank, String first, String last) {
    this.rank = rank;
    this.first = first;
    this.last = last;
  }


}

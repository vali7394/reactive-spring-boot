package com.springReactive.restClient.json;

/**
 * Created by mshaik on 1/15/19.
 */
public class JokeRespone {

  private String type;

  private Value value;


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Value getValue() {
    return value;
  }

  public void setValue(Value value) {
    this.value = value;
  }
}

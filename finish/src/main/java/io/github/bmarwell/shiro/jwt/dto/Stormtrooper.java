package io.github.bmarwell.shiro.jwt.dto;

import javax.json.bind.annotation.JsonbCreator;

public record Stormtrooper(String id, String planetOfOrigin, String species, String type) {

  @JsonbCreator
  public Stormtrooper {
  }

  public Stormtrooper(String planetOfOrigin, String species, String type) {
    this("", planetOfOrigin, species, type);
  }
}

package io.github.bmarwell.shiro.jwt.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class Stormtrooper {

  private String id;

  private String planetOfOrigin;

  private String species;

  private String type;

  public Stormtrooper() {
  }

  public Stormtrooper(String id, String planetOfOrigin, String species, String type) {
    this.id = id; /* can be null if user-supplied */
    this.planetOfOrigin = planetOfOrigin;
    this.species = species;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public String getPlanetOfOrigin() {
    return planetOfOrigin;
  }

  public String getSpecies() {
    return species;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    Stormtrooper that = (Stormtrooper) other;

    return Objects.equals(this.id, that.id)
        && planetOfOrigin.equals(that.planetOfOrigin)
        && species.equals(that.species)
        && type.equals(that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, planetOfOrigin, species, type);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Stormtrooper.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("planetOfOrigin='" + planetOfOrigin + "'")
        .add("species='" + species + "'")
        .add("type='" + type + "'")
        .toString();
  }
}

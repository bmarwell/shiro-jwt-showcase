package io.github.bmarwell.shiro.jwt.service;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface StormtrooperDao extends Serializable {

  Collection<Stormtrooper> listStormtroopers();

  Optional<Stormtrooper> getStormtrooper(String id);

  Stormtrooper addStormtrooper(Stormtrooper trooper);

  Stormtrooper updateStormtrooper(String id, Stormtrooper updatedTrooper);

  void deleteStormtrooper(String id);
}

package io.github.bmarwell.shiro.jwt.service;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StormtrooperDaoImpl implements StormtrooperDao {

  private static final Map<String, Stormtrooper> STORMTROOPERS = new ConcurrentHashMap<>();

  private static final Random random = new SecureRandom();

  @Override
  public Collection<Stormtrooper> listStormtroopers() {
    return STORMTROOPERS.values();
  }

  @Override
  public Optional<Stormtrooper> getStormtrooper(String id) {
    return Optional.ofNullable(STORMTROOPERS.get(id));
  }

  @Override
  public Stormtrooper addStormtrooper(Stormtrooper trooper) {
    // make sure *we* assign the number.
    String id = String.format(Locale.ROOT, "FN-%5d", generateRandomId());
    final Stormtrooper stormtrooper = new Stormtrooper(id, trooper.getPlanetOfOrigin(), trooper.getSpecies(), trooper.getType());
    STORMTROOPERS.put(id, stormtrooper);

    return STORMTROOPERS.get(id);
  }

  @Override
  public Stormtrooper updateStormtrooper(String id, Stormtrooper updatedTrooper) {
    // make sure the ID matches
    final Stormtrooper stormtrooper = new Stormtrooper(id, updatedTrooper.getPlanetOfOrigin(), updatedTrooper.getSpecies(), updatedTrooper.getType());
    STORMTROOPERS.put(id, stormtrooper);

    return STORMTROOPERS.get(id);
  }

  @Override
  public void deleteStormtrooper(String id) {
    STORMTROOPERS.remove(id);
  }

  int generateRandomId() {
    return random.nextInt(10000, 99999);
  }
}

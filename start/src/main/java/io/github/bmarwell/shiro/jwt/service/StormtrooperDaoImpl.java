/*
 * Copyright (C) 2022 The shiro-jjwt-showcase team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    final Stormtrooper stormtrooper = new Stormtrooper(id, trooper.planetOfOrigin(), trooper.species(), trooper.type());
    STORMTROOPERS.put(id, stormtrooper);

    return STORMTROOPERS.get(id);
  }

  @Override
  public Stormtrooper updateStormtrooper(String id, Stormtrooper updatedTrooper) {
    // make sure the ID matches
    final Stormtrooper stormtrooper = new Stormtrooper(id, updatedTrooper.planetOfOrigin(), updatedTrooper.species(), updatedTrooper.type());
    STORMTROOPERS.put(id, stormtrooper);

    return STORMTROOPERS.get(id);
  }

  @Override
  public void deleteStormtrooper(String id) {
    STORMTROOPERS.remove(id);
  }

  @Override
  public void deleteAllStormTroopers() {
    STORMTROOPERS.clear();
  }

  int generateRandomId() {
    return random.nextInt(10000, 99999);
  }
}

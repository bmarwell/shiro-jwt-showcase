package io.github.bmarwell.shiro.jwt.it;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class StormTrooperFactory {

  private static final List<String> PLANETS = List.of(
      "Alderaan",
      "Bespin",
      "Cantonica",
      "Cato Neimoidia",
      "Coruscant",
      "Crait",
      "D'Qar",
      "Endor",
      "Felucia",
      "Hoth",
      "Jakku",
      "Kamino",
      "Kashyyyk",
      "Kef Bir",
      "Korriban",
      "Mimban",
      "Moraband",
      "Naboo",
      "Subterrel",
      "Tatooine",
      "Zonama Sekot"
  );

  private static final List<String> SPECIES = List.of(
      "Ewok",
      "Gungan",
      "Human",
      "Hutt",
      "Jawa",
      "Kaminoan",
      "Mirialan",
      "Mon Calamari",
      "Neimoidian",
      "Ortolan",
      "Rodian",
      "Sith",
      "Twi'Lek",
      "Twi'lek",
      "Wookie",
      "Zabrak"
  );

  private static final List<String> TYPES = List.of("Basic", "Space", "Aquatic", "Marine", "Jump", "Sand");

  private final Random random = new SecureRandom();

  public Stormtrooper createRandomTrooper() {
    final String planet = PLANETS.get(random.nextInt(0, PLANETS.size() - 1));
    final String species = SPECIES.get(random.nextInt(0, SPECIES.size() - 1));
    final String type = TYPES.get(random.nextInt(0, TYPES.size() - 1));

    return new Stormtrooper(planet, species, type);
  }

}

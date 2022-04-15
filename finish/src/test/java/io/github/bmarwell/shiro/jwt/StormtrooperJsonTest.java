package io.github.bmarwell.shiro.jwt;

import io.github.bmarwell.shiro.jwt.dto.Stormtrooper;
import io.github.bmarwell.shiro.jwt.json.JsonbConfigProvider;
import java.util.Map;
import javax.json.bind.Jsonb;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class StormtrooperJsonTest {

  private final JsonbConfigProvider jsonbProvider = new JsonbConfigProvider();

  private final Jsonb JSONB = jsonbProvider.getContext(Stormtrooper.class);

  @AfterEach
  public void closeJson() throws Exception {
    JSONB.close();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void toJson() {
    final Stormtrooper stormtrooper = new Stormtrooper("", "Terra", "Human", "Space");

    final String stormtrooperJson = JSONB.toJson(stormtrooper);

    // then
    final Map map = JSONB.fromJson(stormtrooperJson, Map.class);
    Assertions.assertThat(map)
        .containsEntry("id", "")
        .containsEntry("planet_of_origin", "Terra")
        .containsEntry("species", "Human")
        .containsEntry("type", "Space")
    ;
  }
}

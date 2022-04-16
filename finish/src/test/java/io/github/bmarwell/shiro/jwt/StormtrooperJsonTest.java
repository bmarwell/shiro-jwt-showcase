/*
 * Copyright (C) 2022 Benjamin Marwell
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

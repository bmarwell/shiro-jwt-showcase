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

package io.github.bmarwell.shiro.jwt.json;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonbConfigProvider implements ContextResolver<Jsonb> {

  @Override
  public Jsonb getContext(Class<?> type) {
    JsonbConfig config = getJsonbConfig();
    return JsonbBuilder.newBuilder().withConfig(config).build();
  }

  private JsonbConfig getJsonbConfig() {
    final StormtrooperDeserializer stormtrooperDeserializer = new StormtrooperDeserializer();
    return new JsonbConfig()
        .withNullValues(true)
        .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES)
        .withSerializers(stormtrooperDeserializer)
        .withDeserializers(stormtrooperDeserializer)
        .withEncoding("UTF-8")
        ;
  }
}

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

package io.github.bmarwell.shiro.issuer.json;

import io.jsonwebtoken.io.SerializationException;
import io.jsonwebtoken.io.Serializer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

@Default
@ApplicationScoped
public class JsonbSerializer implements Serializer<Map<String, ?>> {

  private static final JsonbConfig JSONB_CONFIG = new JsonbConfig()
      .withFormatting(Boolean.TRUE);

  private static final Jsonb JSONB = JsonbBuilder.create(JSONB_CONFIG);

  @PreDestroy
  public void destroy() {
    try {
      JSONB.close();
    } catch (Exception javaLangException) {
      throw new IllegalStateException(javaLangException);
    }
  }

  @Override
  public byte[] serialize(Map<String, ?> stringMap) throws SerializationException {
    return JSONB
        .toJson(stringMap)
        .strip()
        .getBytes(StandardCharsets.UTF_8);
  }

}

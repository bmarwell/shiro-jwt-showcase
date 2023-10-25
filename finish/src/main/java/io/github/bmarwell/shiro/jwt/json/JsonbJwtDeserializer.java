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

import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

@Default
@ApplicationScoped
public class JsonbJwtDeserializer implements Deserializer<Map<String, ?>> {

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

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, ?> deserialize(byte[] bytes) throws DeserializationException {
    try (final var inputStream = new ByteArrayInputStream(bytes)) {
      return JSONB.fromJson(inputStream, Map.class);
    } catch (IOException javaIoIOException) {
      throw new DeserializationException(javaIoIOException.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, ?> deserialize(Reader reader) throws DeserializationException {
    return JSONB.fromJson(reader, Map.class);
  }

}

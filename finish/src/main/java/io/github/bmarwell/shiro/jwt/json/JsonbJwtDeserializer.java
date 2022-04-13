package io.github.bmarwell.shiro.jwt.json;

import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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

  @Override
  public Map<String, ?> deserialize(byte[] bytes) throws DeserializationException {
    try (final var inputStream = new ByteArrayInputStream(bytes)) {
      return JSONB.fromJson(inputStream, Map.class);
    } catch (IOException javaIoIOException) {
      throw new DeserializationException(javaIoIOException.getMessage());
    }
  }

}

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
        .getBytes(StandardCharsets.UTF_8);
  }

}

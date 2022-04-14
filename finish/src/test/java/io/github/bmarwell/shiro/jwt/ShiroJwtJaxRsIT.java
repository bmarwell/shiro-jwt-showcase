package io.github.bmarwell.shiro.jwt;

import java.net.URI;
import org.junit.jupiter.api.TestInstance;

@SuppressWarnings("NewClassNamingConvention")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShiroJwtJaxRsIT extends AbstractShiroJaxRsIT {

  @Override
  protected URI getBaseUri() {
    return URI.create("http://localhost:" + System.getProperty("http.port") + "/" + System.getProperty("app.context.root"));
  }
}

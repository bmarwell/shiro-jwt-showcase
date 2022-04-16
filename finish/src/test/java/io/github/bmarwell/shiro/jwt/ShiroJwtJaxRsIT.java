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

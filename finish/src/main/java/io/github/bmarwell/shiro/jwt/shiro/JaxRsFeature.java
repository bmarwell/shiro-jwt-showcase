package io.github.bmarwell.shiro.jwt.shiro;

import javax.enterprise.context.Dependent;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import org.apache.shiro.web.jaxrs.ShiroFeature;

@Dependent
@Provider
public class JaxRsFeature extends ShiroFeature {

  @Override
  public boolean configure(FeatureContext context) {
    return super.configure(context);
  }

}

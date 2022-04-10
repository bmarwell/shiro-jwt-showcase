package io.github.bmarwell.shiro.jwt.shiro;

import javax.enterprise.context.Dependent;
import javax.servlet.annotation.WebListener;
import org.apache.shiro.web.env.EnvironmentLoaderListener;

@Dependent
@WebListener
public class EnvListener extends EnvironmentLoaderListener {

  public EnvListener() {
    super();
  }
}

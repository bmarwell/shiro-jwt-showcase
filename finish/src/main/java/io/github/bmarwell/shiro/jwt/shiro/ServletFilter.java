package io.github.bmarwell.shiro.jwt.shiro;


import javax.enterprise.context.Dependent;
import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;

@Dependent
@WebFilter(
    asyncSupported = true,
    urlPatterns = {"/*"},
    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR, DispatcherType.ASYNC}
)
public class ServletFilter extends org.apache.shiro.web.servlet.ShiroFilter {
}

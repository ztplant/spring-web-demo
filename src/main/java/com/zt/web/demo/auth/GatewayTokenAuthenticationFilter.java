package com.zt.web.demo.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class GatewayTokenAuthenticationFilter extends OncePerRequestFilter {

  public static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";

  private AuthenticationManager authenticationManager;
  private BasicAuthenticationEntryPoint basicAuthEntryPoint = new BasicAuthenticationEntryPoint();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      GatewayTokenAuthenticationToken token = obtainToken(request);

      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      Authentication authResult = getAuthenticationManager().authenticate(token);
      logger.info("GatewayTokenAuthentication success: " + authResult);
      SecurityContextHolder.getContext().setAuthentication(authResult);
    } catch (AuthenticationException failed) {
      SecurityContextHolder.clearContext();

      logger.warn("GatewayTokenAuthentication request for failed: " + failed);

      basicAuthEntryPoint.commence(request, response, failed);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private GatewayTokenAuthenticationToken obtainToken(HttpServletRequest request) {
    String header = request.getHeader(AUTHORIZATION);
    if (header == null) {
      return null;
    }
    header = header.trim();
    if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BEARER)) {
      return null;
    }

    return new GatewayTokenAuthenticationToken(header.substring(7));
  }

  public AuthenticationManager getAuthenticationManager() {
    return authenticationManager;
  }

  public void setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
}

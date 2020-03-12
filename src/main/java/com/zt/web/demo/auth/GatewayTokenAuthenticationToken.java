package com.zt.web.demo.auth;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class GatewayTokenAuthenticationToken extends AbstractAuthenticationToken {

  Logger logger = LoggerFactory.getLogger(GatewayTokenAuthenticationToken.class);

  private static final String GATEWAY_TOKEN_NAME = "AFC_GATEWAY_TOKEN";
  private boolean isAuthenticated;
  private Object credentials;

  /**
   * Creates a token with the supplied array of authorities.
   *
   * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented
   * by this authentication object.
   */
  public GatewayTokenAuthenticationToken(Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.credentials = credentials;
    super.setAuthenticated(true);
    logger.info("GatewayTokenAuthenticationToken setAuthenticated -> true loading...");
  }

  public GatewayTokenAuthenticationToken(String credentials) {
    super(null);
    this.credentials = credentials;
    this.isAuthenticated = false;
    logger.info("GatewayTokenAuthenticationToken setAuthenticated -> false loading...");
  }

  @Override
  public void setAuthenticated(boolean authenticated){
    if (authenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }
    super.setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public String getName() {
    return GATEWAY_TOKEN_NAME;
  }
}

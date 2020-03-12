package com.zt.web.demo.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class GatewayTokenAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsService myUserDetailsService;
  private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    GatewayTokenAuthenticationToken token = (GatewayTokenAuthenticationToken) authentication;
    UserDetails userDetails = myUserDetailsService
        .loadUserByUsername((String) token.getCredentials());
    if (userDetails == null) {
      throw new InternalAuthenticationServiceException("Unable to obtain user information");
    }
    GatewayTokenAuthenticationToken authenticationResult = new GatewayTokenAuthenticationToken(
        token.getCredentials(), this.authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
    return authenticationResult;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return GatewayTokenAuthenticationToken.class.isAssignableFrom(authentication);
  }

  public UserDetailsService getMyUserDetailsService() {
    return myUserDetailsService;
  }

  public void setMyUserDetailsService(
      UserDetailsService myUserDetailsService) {
    this.myUserDetailsService = myUserDetailsService;
  }


}

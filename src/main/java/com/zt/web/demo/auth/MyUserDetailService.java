package com.zt.web.demo.auth;

import java.util.Collections;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

  private String gatewayToken;

  public MyUserDetailService(String gatewayToken) {
    this.gatewayToken = gatewayToken;
  }

  @Override
  public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
    if(this.gatewayToken.equals(token)){
      return new User("funding", token, true,
          true, true,
          true, Collections.emptySet());
    }
    return null;
  }

}

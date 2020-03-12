package com.zt.web.demo.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${gateway.token}")
  private String gatewayToken;

  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/demo/**").authenticated();
    http.apply(new GatewayLoginAuthenticationSecurityConfig(gatewayToken));

  }
}

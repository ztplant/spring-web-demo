package com.zt.web.demo.auth.config;

import com.zt.web.demo.auth.GatewayTokenAuthenticationFilter;
import com.zt.web.demo.auth.GatewayTokenAuthenticationProvider;
import com.zt.web.demo.auth.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

public class GatewayLoginAuthenticationSecurityConfig extends
    AbstractHttpConfigurer<GatewayLoginAuthenticationSecurityConfig, HttpSecurity> {

  private static final Logger logger = LoggerFactory.getLogger(GatewayLoginAuthenticationSecurityConfig.class);

  private String gatewayToken;

  public GatewayLoginAuthenticationSecurityConfig(String gatewayToken) {
    this.gatewayToken = gatewayToken;
    logger.info("GatewayLoginAuthenticationSecurityConfig is loading ... ");
  }

  @Override
  public void configure(HttpSecurity http){
    GatewayTokenAuthenticationFilter gatewayTokenAuthenticationFilter = new GatewayTokenAuthenticationFilter();
    gatewayTokenAuthenticationFilter.setAuthenticationManager(http.getSharedObject(
        AuthenticationManager.class));

    GatewayTokenAuthenticationProvider provider = new GatewayTokenAuthenticationProvider();
    if(StringUtils.isEmpty(gatewayToken)){
      logger.warn("WARNING!!!!!! there is no gateway token, please check your config properties");
    }
    provider.setMyUserDetailsService(new MyUserDetailService(gatewayToken));

    http.authenticationProvider(provider).addFilterAfter(gatewayTokenAuthenticationFilter,
        BasicAuthenticationFilter.class);
  }
}

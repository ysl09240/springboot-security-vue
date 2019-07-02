package com.sykean.hmhc.security;

import com.sykean.hmhc.common.Constants;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.exception.UserStopException;
import com.sykean.hmhc.service.UserService;
import com.sykean.hmhc.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenFileter tokenFileter;

    @Autowired
    private SuccessAuthenticationHandler successAuthenticationHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MD5PasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(Constants.Security.WITHOUT_AUTH);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().access("@permissionService.hasPermission(request)")
                .and()
                .formLogin().loginProcessingUrl("/login")
                .permitAll()
                .successHandler(successAuthenticationHandler)
                .failureHandler((req, res, e) -> {
                    if (e.getCause() instanceof UserStopException) {
                        ResponseUtil.printRes(res, ResponseCode.USER_STOP, false, null);
                    } else {
                        ResponseUtil.printRes(res, ResponseCode.LOGIN_FAILED, false, null);
                    }

                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, res, authentication) -> {
                    ResponseUtil.printRes(res, ResponseCode.LOGOUT_SUCCESS, true, null);
                })
                .permitAll()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler((req, res, e) -> {
            ResponseUtil.printRes(res, ResponseCode.ACCESS_DENIED, false, null);
        }).authenticationEntryPoint((req, res, e) -> {
            ResponseUtil.printRes(res, ResponseCode.TOEKN_INVALID, false, null);
        })
                .and()
                .addFilterAfter(tokenFileter, UsernamePasswordAuthenticationFilter.class);
    }
}
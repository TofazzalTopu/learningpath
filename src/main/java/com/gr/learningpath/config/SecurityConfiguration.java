//package com.gr.learningpath.config;
//
//import com.gr.learningpath.security.jwt.JwtConfigurer;
//import com.gr.learningpath.security.jwt.JwtTokenProvider;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
//import org.springframework.web.filter.CorsFilter;
//import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
//
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Import(SecurityProblemSupport.class)
//@EnableWebSecurity
//@Log4j2
//@Configuration
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final SecurityProblemSupport problemSupport;
//    private final CorsFilter corsFilter;
//
//    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider, SecurityProblemSupport problemSupport, CorsFilter corsFilter) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.problemSupport = problemSupport;
//        this.corsFilter = corsFilter;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .authenticationEntryPoint(problemSupport)
//                .accessDeniedHandler(problemSupport)
//                .and()
//                .headers()
//                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data: blob:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:;object-src 'self' blob:")
//                .and()
//                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
//                .and()
//                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
//                .and()
//                .frameOptions()
//                .deny()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/api/login",
//                        "/api/register"
//                ).permitAll()
//                .antMatchers("/api/**").authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .apply(securityConfigurerAdapter());
//    }
//
//    private JwtConfigurer securityConfigurerAdapter() {
//        return new JwtConfigurer(jwtTokenProvider);
//    }
//}

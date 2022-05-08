package com.gr.learningpath.config.secuirty;

import com.gr.learningpath.constant.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
@ComponentScan(basePackages = "com.gr.learningpath")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsFilter corsFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final UserDetailsService jwtUserDetailsService;
	private final JwtRequestFilter jwtRequestFilter;
	private final SecurityProblemSupport problemSupport;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
//		httpSecurity.cors();
		httpSecurity
				.csrf()
				.disable()
				.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(problemSupport)
				.accessDeniedHandler(problemSupport)
				.and()
				.headers()
				.contentSecurityPolicy("default-src 'self'; frame-src 'self' data: blob:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:;object-src 'self' blob:")
				.and()
				.referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
				.and()
				.featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
				.and()
				.frameOptions()
				.deny()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(
						"/api/login",
						"/api/register"
				).permitAll()
				.antMatchers("/api/**").authenticated()
				.and()
				.httpBasic();
//				.and()
//				.apply(securityConfigurerAdapter());
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers(AppConstants.AUTH_WHITELIST).permitAll()
				.anyRequest()
				.permitAll()
				.and()
				.formLogin()
				.permitAll()
				.and()
				.logout()
				.invalidateHttpSession(true)
				.deleteCookies()
				.and()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//		httpSecurity.csrf().disable()
//				.authorizeRequests()
//				.antMatchers(AppConstants.AUTH_WHITELIST).permitAll()
//				.anyRequest()
//				.permitAll()
//				.and()
//				.formLogin()
//				.permitAll()
//				.and()
//				.logout()
//				.invalidateHttpSession(true)
//				.deleteCookies()
//				.and()
//				// make sure we use stateless session; session won't be used to
//				// store user's state.
//				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//				.and()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		// Add a filter to validate the tokens with every request
//		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

}

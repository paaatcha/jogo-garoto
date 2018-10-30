package com.garoto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()		
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/dashboard/**").authenticated() //"/swagger-ui.html" "/api/**"
				.antMatchers("/", "/login").anonymous()
			.and().formLogin()
				.loginPage("/")
				.defaultSuccessUrl("/dashboard")
				.failureUrl("/?error=true")
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/login")
			.and()
				.logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/");
					
		
	}
	
	/**
	 * This bean will be used to encode users' passwords.
	 * @return a BCrypt-based encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package com.icesi.edu.taller.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


//ci-201-mvc-tutorial-val-steps-sec-ini


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {	

		//Autorización según rol
		
		httpSecurity.authorizeRequests().antMatchers("/topics/").hasRole("YES").antMatchers("/**").authenticated().anyRequest().permitAll()
		.and().formLogin().loginPage("/login").permitAll().and().httpBasic().and().logout().invalidateHttpSession(true).clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		
	}
	
	//
}
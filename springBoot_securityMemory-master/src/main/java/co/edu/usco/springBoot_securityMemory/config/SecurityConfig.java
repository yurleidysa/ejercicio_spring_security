package co.edu.usco.springBoot_securityMemory.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

		manager.setUsersByUsernameQuery(
				"SELECT username, password, enabled FROM users WHERE username = ?");

		manager.setAuthoritiesByUsernameQuery(
				"SELECT u.username, r.name FROM users u "
				+ "JOIN users_roles ur ON u.user_id = ur.user_id "
				+ "JOIN roles r ON ur.role_id = r.role_id "
				+ "WHERE u.username = ?");

		return manager;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login.html", "/403.html", "/csrf.js").permitAll()
				.requestMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
				.requestMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
				.requestMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
				.requestMatchers("/delete/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/login.html")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/menu.html", true)
				.failureUrl("/login.html?error")
				.permitAll()
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login.html?logout")
				.permitAll()
			)
			.csrf(csrf -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
			)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.exceptionHandling(ex -> ex.accessDeniedPage("/403.html"));
		return http.build();
	}
}
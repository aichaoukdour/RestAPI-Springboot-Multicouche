package com.example.RestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.RestAPI.Enumeration.RoleEnum;

@SpringBootApplication(scanBasePackages = "com.example.RestAPI")
@EnableCaching
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		String encodedAdminPassword = passwordEncoder.encode("admin");
		String encodedUserPassword = passwordEncoder.encode("user");
		String encodedManagerPassword = passwordEncoder.encode("manager");

		UserDetails admin = User.builder()
				.username("admin")
				.password(encodedAdminPassword)
				.roles(RoleEnum.ADMIN.name())
				.build();

		UserDetails user = User.builder()
				.username("user")
				.password(encodedUserPassword)
				.roles(RoleEnum.USER.name())
				.build();

		UserDetails manager = User.builder()
				.username("manager")
				.password(encodedManagerPassword)
				.roles(RoleEnum.MANAGER.name())
				.build();

		return new InMemoryUserDetailsManager(admin, user, manager);
	}

}

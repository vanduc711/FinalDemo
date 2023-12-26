package com.tutorial.finaldemo;

import com.tutorial.finaldemo.config.ContractSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class SpringSecurityApplication{
	public static void main(String[] args) { SpringApplication.run(SpringSecurityApplication.class, args);

		try {
			new ContractSocketConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

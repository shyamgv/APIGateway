package com.example;

import com.example.Filters.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@EnableZuulProxy
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@RestController
public class ZuulProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyApplication.class, args);
	}

	@RequestMapping({ "/user" })
	public Map<String, Object> user(Principal principal) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
		map.put("credentials", oAuth2Authentication.getCredentials());
		map.put("Auth2Request", oAuth2Authentication.getOAuth2Request());
		map.put("UserAuthentication", oAuth2Authentication.getUserAuthentication());
		map.put("Authorities", oAuth2Authentication.getAuthorities());
/*		map.put("Details", oAuth2Authentication.getDetails());
		map.put("Class", oAuth2Authentication.getClass());*/

		return map;
	}

	@Bean
	public LoggingFilter loggingFilter(){
		return new LoggingFilter();
	}

}

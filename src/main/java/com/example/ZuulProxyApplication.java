package com.example;

import com.example.Filters.LoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SpringBootApplication
@EnableZuulProxy
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@RestController
public class ZuulProxyApplication {

	@Autowired
	private OAuth2RestOperations oAuth2RestOperations;

	@Bean(name = "GreeterRestTemplate")
	@LoadBalanced
	public OAuth2RestTemplate restTemplate() {
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oAuth2RestOperations.getResource(),oAuth2RestOperations.getOAuth2ClientContext());
		return restTemplate;
	}
	@Autowired
	@LoadBalanced
	@Qualifier("GreeterRestTemplate")
	protected OAuth2RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyApplication.class, args);
	}

	@RequestMapping("/home")
	public String home(String salutation, String name) {
		OAuth2AccessToken access_token = oAuth2RestOperations.getAccessToken();
		System.out.println(access_token);
		URI uri = UriComponentsBuilder.fromUriString("http://GREETER"+"/services/hello")
				.queryParam("salutation", salutation)
				.queryParam("name", name)
				.build()
				.toUri();

		Greeting greeting = restTemplate.getForObject(uri, Greeting.class);

		return greeting.getMessage();
	}

	@Bean
	public LoggingFilter loggingFilter(){
		return new LoggingFilter();
	}

}

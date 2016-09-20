package com.example;

import com.example.Filters.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@RestController
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
public class ZuulProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyApplication.class, args);
	}

	@Bean
	public LoggingFilter loggingFilter(){
		return new LoggingFilter();
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json")
	public Greeting greeting() {
		//log.info(String.format("Now saying \"%s\" to %s", salutation, name));
		return new Greeting("Sairam");
	}

}

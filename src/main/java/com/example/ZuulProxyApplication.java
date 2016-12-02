package com.example;

import com.example.Filters.LoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by Shyam on 8/2/2016.
 * @EnableZuulProxy is a convinent annotation that makes this application act as an APIGateway,
 * register the application to the service registry and also act as a circuit breaker.
 * @SpringBootApplication is a convinent annotation that has ComponentScan (scans all the components
 * under this directory structure), Configuration (can host number of beans), EnableAutoConfiguration
 * (attempts to guess and configure beans that are likely needed - helps start embedded tomcat in this case ).
 * @RestController enables to define RESTFul end-points
 * */

@EnableZuulProxy
@SpringBootApplication
@RestController
public class ZuulProxyApplication {

    // OAuth2RestOperations, provides resources like clientID, Client_secret, token and authorization URI.
    // Also provides Client Context, required for populate RestTemplate to call another microservice acting ResourceServer.
    @Autowired
    private OAuth2RestOperations oAuth2RestOperations;
    @Autowired
    private Message message;

    @Bean(name = "GreeterRestTemplate")
    @LoadBalanced
    public OAuth2RestTemplate restTemplate() {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oAuth2RestOperations.getResource(), oAuth2RestOperations.getOAuth2ClientContext());
        return restTemplate;
    }

    @Autowired
    @LoadBalanced
    @Qualifier("GreeterRestTemplate")
    protected OAuth2RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = "application/json")
    public Greeting home(String salutation, String name) {
       return sayHome(salutation,name);
    }

    public Greeting sayHome(String salutation, String name) {
        OAuth2AccessToken access_token = oAuth2RestOperations.getAccessToken();
        System.out.println(access_token);
        URI uri = UriComponentsBuilder.fromUriString("http://GREETER" + "/services/hello")
                .queryParam("salutation", salutation)
                .queryParam("name", name)
                .build()
                .toUri();

        Greeting greeting = restTemplate.getForObject(uri, Greeting.class);

        return new Greeting(greeting.getMessage(),this.message.getMessage());
    }

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

}

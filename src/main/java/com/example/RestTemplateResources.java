package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Component;

@EnableOAuth2Client
@Component
public class RestTemplateResources extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateResources.class);

    @Value("${security.oauth2.client.access-token-uri}")
    private String accessTokenUri;

    @Value("${security.oauth2.client.user-authorization-uri}")
    private String userAuthorizationUri;

    @Value("${security.oauth2.client.client-id}")
    private String clientID;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    /*@Value("${security.oauth2.client.grantType}")
    private String grantType;
*/

    /**
     * An opinionated WebApplicationInitializer to run a SpringApplication from a traditional WAR deployment.
     * Binds Servlet, Filter and ServletContextInitializer beans from the application context to the servlet container.
     *
     * @link http://docs.spring.io/spring-boot/docs/current/api/index.html?org/springframework/boot/context/web/SpringBootServletInitializer.html
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestTemplateResources.class);
    }

    /**
     * The heart of our interaction with the resource; handles redirection for authentication, access tokens, etc.
     * @param oauth2ClientContext
     * @return
     */
    @Bean(name = "GreeterRestTemplate")
    public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
        System.out.println("Inside restTemplate");
        return new OAuth2RestTemplate(resource(), oauth2ClientContext);
    }

    private OAuth2ProtectedResourceDetails resource() {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
        resource.setClientId(clientID);
        System.out.println("clientID===>>>"+clientID);
        resource.setClientSecret(clientSecret);
        resource.setAccessTokenUri(accessTokenUri);
        resource.setUserAuthorizationUri(userAuthorizationUri);
        //resource.setScope(Arrays.asList("read"));

        return resource;
    }

}
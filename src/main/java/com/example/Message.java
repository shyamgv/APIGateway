package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by Shyam on 10/20/2016.
 * @Component makes this class a component so that it can be Autowired
 * @RefreshScope tells that when ever /refresh end point is hit this bean would get refreshed with any changes in the config server
 */
@Component
@RefreshScope
public class Message {
    @Value("${my.message}")
    String message;

    public String getMessage() {
        return message;
    }
}

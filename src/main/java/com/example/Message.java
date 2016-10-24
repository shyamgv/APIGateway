package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by gundave on 10/20/2016.
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

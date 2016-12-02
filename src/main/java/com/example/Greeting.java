package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gundave on 9/12/2016.
 */
public class Greeting {

    private String message;
    private String status;

    @JsonCreator
    public Greeting(@JsonProperty("message") String message,@JsonProperty("status") String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        String str = this.message + this.status;
        return str;
    }
}


package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gundave on 9/12/2016.
 */
public class Greeting {

        private String message;

        @JsonCreator
        public Greeting(@JsonProperty("message") String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }


}

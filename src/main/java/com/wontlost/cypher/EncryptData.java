package com.wontlost.cypher;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class EncryptData {

    static final Map<String, String> innerKey = new HashMap<>();

    @Bean
    public static Map<String, String> innerKey() {
        return innerKey;
    }

}

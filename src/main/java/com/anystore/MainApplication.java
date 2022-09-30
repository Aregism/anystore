package com.anystore;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("spring.datasource.username", args[0]);
        properties.put("spring.datasource.password", args[1]);
        properties.put("spring.mail.username", args[2]);
        properties.put("spring.mail.password", args[3]);
        new SpringApplicationBuilder(MainApplication.class)
                .properties(properties).run(args);
    }
}
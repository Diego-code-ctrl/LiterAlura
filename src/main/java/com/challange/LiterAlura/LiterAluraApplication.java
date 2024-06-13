package com.challange.LiterAlura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class LiterAluraApplication {
    
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

}
